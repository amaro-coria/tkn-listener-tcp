/**
 * Teknei 2016
 */
package com.teknei.service;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.teknei.service.client.ReceNaveRestClient;

/**
 * Handles TPC connections
 * 
 * @author Jorge Amaro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class MinaTCPHandler extends IoHandlerAdapter {

	/**
	 * Default-constructor
	 */
	public MinaTCPHandler() {
	}

	/**
	 * Param args constructor
	 * 
	 * @param client
	 */
	public MinaTCPHandler(ReceNaveRestClient client) {
		super();
		this.client = client;
	}

	private static final Logger log = LoggerFactory.getLogger(MinaTCPHandler.class);
	private final String regexPattern = "F1,\\d+,\\w+,[-]?\\d+.\\d+,[-]?\\d+.\\d+,\\w+";
	private ReceNaveRestClient client;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandlerAdapter#exceptionCaught(org.apache.
	 * mina.core.session.IoSession, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error("Exception listening to: {} with error: {}", session.getLocalAddress().toString(),
				cause.getMessage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.
	 * mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String strMessage = new String(((IoBuffer) message).array()).trim();
		log.info("Message received: |{}|", strMessage);
		if (strMessage.matches(regexPattern)) {
			log.debug(strMessage.toString());
			String[] messageSplited = strMessage.toString().split(",");
			Integer idVehi = Integer.parseInt(messageSplited[1]);
			Long idRecoNave = Long.parseLong(messageSplited[2], 16);
			Double latitude = Double.parseDouble(messageSplited[3]);
			Double longitude = Double.parseDouble(messageSplited[4]);
			Long epoch = Long.parseLong(messageSplited[5], 16);
			saveReceNave(idVehi, idRecoNave, latitude, longitude, epoch);
			StringBuilder sb = new StringBuilder();
			sb.append(idVehi);
			sb.append(",");
			sb.append(Long.toHexString(idRecoNave));
			log.info("Returning: {}", sb.toString());
			session.write(IoBuffer.wrap(sb.toString().getBytes()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandlerAdapter#sessionIdle(org.apache.mina
	 * .core.session.IoSession, org.apache.mina.core.session.IdleStatus)
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.info("Session idle: {}", session.getLocalAddress().toString());
	}

	/**
	 * Transforms and forwards the request to the remote REST service
	 * 
	 * @param idVehi
	 *            - the id of the vehicle
	 * @param idRecoNave
	 *            - the id of the record
	 * @param lat
	 *            - the latitude
	 * @param lont
	 *            - the longitude
	 * @param epoch
	 *            - the unix date
	 */
	private void saveReceNave(Integer idVehi, Long idRecoNave, Double lat, Double lont, Long epoch) {
		client.saveReceNave(idVehi, idRecoNave, lat, lont, epoch);
	}

}
