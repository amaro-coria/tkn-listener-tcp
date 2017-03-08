package com.teknei.service.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("tcp-api")
public interface ReceNaveRestClient {

	@RequestMapping(value = "recenave/save/{idVehi}/{idRecoNave}/{lat}/{lot}/{epoch}", method = RequestMethod.GET)
	public int saveReceNave(@PathVariable("idVehi") Integer idVehi, @PathVariable("idRecoNave") Long idRecoNave,
			@PathVariable("lat") Double lat, @PathVariable("lot") Double lot, @PathVariable("epoch") Long epoch);

}
