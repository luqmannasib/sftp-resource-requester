package org.mule.modules.sftpresourcerequester;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.inject.Inject;

import org.mule.api.DefaultMuleException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.transformer.Transformer;
import org.mule.modules.sftpresourcerequester.error.ErrorHandler;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.transport.NullPayload;

@Connector(name = "sftp-resource-requester", friendlyName = "SFTPResourceRequester")
@OnException(handler = ErrorHandler.class)
public class SFTPResourceRequesterConnector {

	private static final int BUFFER_SIZE = 4096;
	@Inject
	private MuleContext muleContext;

	public void setMuleContext(MuleContext muleContext) {
		this.muleContext = muleContext;
	}

	public MuleContext getMuleContext() {
		return muleContext;
	}

	@Processor
	public void requestSFTPResource(MuleEvent muleEvent, String resource, @Optional @Default("1000") long timeout,
			@Optional String returnClass, @Optional Boolean throwExceptionOnTimeout) throws MuleException {
		try {
			URL url = new URL(resource);
			URLConnection conn = url.openConnection();
			InputStream inputStream = conn.getInputStream();

			FileOutputStream outputStream = new FileOutputStream("\\tmp\\Temp");

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			inputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		MuleMessage message = muleContext.getClient().request("file:///tmp//Temp", timeout);

		Object result = null;
		if (message != null) {
			result = message.getPayload();
			if (returnClass != null) {
				try {
					Transformer transformer = muleContext.getRegistry().lookupTransformer(
							DataTypeFactory.create(result.getClass()),
							DataTypeFactory.create(Class.forName(returnClass)));
					result = transformer.transform(result);
				} catch (ClassNotFoundException e) {
					throw new DefaultMuleException(e);
				}
			}
			message.setPayload(result);
			muleEvent.setMessage(message);
		} else if (Boolean.TRUE.equals(throwExceptionOnTimeout)) {
			throw new DefaultMuleException("No message received in the configured timeout - " + timeout);
		} else {
			muleEvent.getMessage().setPayload(NullPayload.getInstance());
		}
	}
}