package co.nz.pizzashack.client.ds

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

import org.springframework.stereotype.Service

import co.nz.pizzashack.client.data.dto.PizzashackDto

@Service
@Slf4j
class PizzashackDSImpl implements PizzashackDS{

	RESTClient client = new RESTClient('http://localhost:8111/rest/v1/', ContentType.JSON)

	@Override
	Set<PizzashackDto> pizzashackItems() {
		log.info "pizzashackItems start:{}"
		def response = client.get(path:'pizzashackList')
		log.debug "status = $response.status"
		Set<PizzashackDto> pizzashacks = response.data
		log.info "pizzashackItems end:{}"
		return pizzashacks
	}
}
