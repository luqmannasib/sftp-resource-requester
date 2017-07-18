
package org.mule.modules.sftpresourcerequester.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.sftpresourcerequester.SFTPResourceRequesterConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>SFTPResourceRequesterConnectorProcessAdapter</code> is a wrapper around {@link SFTPResourceRequesterConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-07-18T09:54:49+01:00", comments = "Build UNNAMED.2793.f49b6c7")
public class SFTPResourceRequesterConnectorProcessAdapter
    extends SFTPResourceRequesterConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<SFTPResourceRequesterConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, SFTPResourceRequesterConnectorCapabilitiesAdapter> getProcessTemplate() {
        final SFTPResourceRequesterConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,SFTPResourceRequesterConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, SFTPResourceRequesterConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, SFTPResourceRequesterConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
