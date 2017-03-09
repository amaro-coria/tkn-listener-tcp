/**
 * Teknei 2016
 */
package com.teknei.service.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign client for remote service
 * @author Jorge Amaro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@FeignClient("tcp-api")
public interface ReceNaveRestClient {

	/**
	 * Forwards the request to the remote api
	 * @param idVehi - the if of the vehicle
	 * @param idRecoNave - the id of the record
	 * @param lat - the latitude
	 * @param lot -the longitude
	 * @param epoch - the unix date
	 * @return
	 */
	@RequestMapping(value = "recenave/save/{idVehi}/{idRecoNave}/{lat}/{lot}/{epoch}", method = RequestMethod.GET)
	public int saveReceNave(@PathVariable("idVehi") Integer idVehi, @PathVariable("idRecoNave") Long idRecoNave,
			@PathVariable("lat") Double lat, @PathVariable("lot") Double lot, @PathVariable("epoch") Long epoch);

}
