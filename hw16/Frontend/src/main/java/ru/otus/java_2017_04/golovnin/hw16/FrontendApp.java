package ru.otus.java_2017_04.golovnin.hw16;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AddressProvider;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AddressesProvideMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageClient;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageProcessor;

public class FrontendApp
{
    private static final String APP_CONTEXT_FILE = "applicationContext.xml";
    private static final String ADDRESS_PROVIDER_BEAN_NAME = "AddressProvider";

    public static void main( String[] args )
    {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(APP_CONTEXT_FILE);
        AddressProvider addressProvider = (AddressProvider) appContext.getBean(ADDRESS_PROVIDER_BEAN_NAME);

        MessageProcessor messageProcessor = new MessageProcessor();
        MessageClient messageClient = new MessageClient(messageProcessor);
        AddressProviderWatcher watcher = new AddressProviderWatcher(addressProvider, messageClient);
        messageProcessor.setHandler(AddressesProvideMessage.class.getSimpleName(), watcher);

        messageClient.start("localhost", 5050);
        watcher.start();

        int frontendServerPort = Integer.parseUnsignedInt(args[0]);
        FrontendServer frontendServer = new FrontendServer(appContext);
        frontendServer.start(frontendServerPort);
    }


}
