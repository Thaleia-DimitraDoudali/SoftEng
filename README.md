# SoftEng
Project for Software Engineering class 2014-2015 @ ECE NTUA
Collaborators: Maria Karouta, Vasiliki Savva

Stand-alone run instructions:

1. Comment out line 295 at net.java.sip.communicator.common.NetworkAddressManager (publicAddress = address;).
2. At /sipproxy/gov/nist/sip/proxy/configuration/configuration.xml change 
    stack_IP_address="192.168.1.202" (local ip)
3. Run /sipproxy/gov/nist/sip/proxy/gui/ProxyLauncher.java in order to put up the proxy server
4. At /SipCommunicator-Fall05/sip-communicator.xml change
    <AUDIO_PORT value="22224"/>
    <VIDEO_PORT value="22222"/>
    <REGISTRAR_ADDRESS value="192.168.1.202:4000"/>
    <REGISTRAR_PORT value="5060"/>
    <DEFAULT_DOMAIN_NAME value="192.168.1.202:4000"/>
    <DEFAULT_AUTHENTICATION_REALM value="192.168.1.202:4000"/>
    <IP_ADDRESS value="192.168.1.202"/>
    <OUTBOUND_PROXY value="192.168.1.202:4000/udp"/>
5. Run /SipCommunicator-Fall05/net/java/sip/communicator/SipCommunicator.java in order to put up the first Sip Communicator
6. At /SipCommunicator-Fall05/sip-communicator.xml change
    <AUDIO_PORT value="22225"/>
    <VIDEO_PORT value="22223"/>
    <REGISTRAR_ADDRESS value="192.168.1.202:4000"/>
    <REGISTRAR_PORT value="5061"/>
    <DEFAULT_DOMAIN_NAME value="192.168.1.202:4000"/>
    <DEFAULT_AUTHENTICATION_REALM value="192.168.1.202:4000"/>
    <IP_ADDRESS value="192.168.1.202"/>
    <OUTBOUND_PROXY value="192.168.1.202:4000/udp"/>
7. Run /SipCommunicator-Fall05/net/java/sip/communicator/SipCommunicator.java in order to put up the second Sip Communicator



