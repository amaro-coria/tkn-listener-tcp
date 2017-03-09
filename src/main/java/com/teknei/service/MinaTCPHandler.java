package com.teknei.service;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.teknei.service.client.ReceNaveRestClient;

public class MinaTCPHandler extends IoHandlerAdapter {
	
	public MinaTCPHandler() {
	}
	
	public MinaTCPHandler(ReceNaveRestClient client){
		super();
		this.client = client;
	}
	
	
	private static final Logger log = LoggerFactory.getLogger(MinaTCPHandler.class);
	private final String regexPattern = "F1,\\d+,\\w+,[-]?\\d+.\\d+,[-]?\\d+.\\d+,\\w+";
	private ReceNaveRestClient client;

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error("Exception listening to: {} with error: {}", session.getLocalAddress().toString(),
				cause.getMessage());
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String strMessage = new String(((IoBuffer)message).array()).trim();
		log.info("Message received: |{}|", strMessage);
		if(strMessage.matches(regexPattern)){
			log.info(strMessage.toString());
			String[] messageSplited = strMessage.toString().split(",");
			Integer idVehi = Integer.parseInt(messageSplited[1]);
			Long idRecoNave = Long.parseLong(messageSplited[2], 16);
			Double latitude = Double.parseDouble(messageSplited[3]);
			Double longitude = Double.parseDouble(messageSplited[4]);
			Long epoch = Long.parseLong(messageSplited[5], 16);
			saveReceNave(idVehi, idRecoNave, latitude, longitude, epoch);
		}
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.info("Session idle: {}", session.getLocalAddress().toString());
	}

	private void saveReceNave(Integer idVehi, Long idRecoNave, Double lat, Double lont, Long epoch){
		 client.saveReceNave(idVehi, idRecoNave, lat, lont, epoch);
	}
	
}
