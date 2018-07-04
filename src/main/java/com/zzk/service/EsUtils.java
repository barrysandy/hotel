package com.zzk.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.plugin.deletebyquery.DeleteByQueryPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service("esUtils")
public class EsUtils {
	
	@Value("${search.cluster.name}")  
	private String CLUSTER_NAME;
	
	@Value("${search.host.ip}")  
	private  String HOST_IP;

//	@Value("${search.host.prot}")
//	private  int HOST_PORT;
	
	@Value("${search.index.name}")  
	public  String INDEX_NAME;
	
	@Value("${search.type.hotel.name}")  
	public  String TYPE_HOTELS;
	
	
	private  Client  client;
	
	public  Client  getEsClient() throws UnknownHostException{		
		if(client==null){
		Settings settings = Settings.settingsBuilder()
                .put("cluster.name",CLUSTER_NAME)
                .build();
		
		client = TransportClient.builder()
                .settings(settings)
                .addPlugin(DeleteByQueryPlugin.class)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(
                                    InetAddress.getByName(HOST_IP), 9300));
		}
		return client;  
	}

	/**
	 * @return the hOST_IP
	 */
	public String getHOST_IP() {
		return HOST_IP;
	}

	/**
	 * @return the iNDEX_NAME
	 */
	public String getINDEX_NAME() {
		return INDEX_NAME;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	public String getTYPE_HOTELS() {
		return TYPE_HOTELS;
	}
	
	
}
