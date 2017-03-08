/**
 * Teknei 2016
 */
package com.teknei;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Main class
 * @author Jorge Amaro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class TknListenerTcpApplication {

	private static final Logger log = LoggerFactory.getLogger(TknListenerTcpApplication.class);
	
	/**
	 * Entry point for the app
	 * @param args
	 */
	public static void main(String[] args) {
		boolean listen = false;
		boolean test = false;
		if (args == null || args.length == 0) {
			log.info(
					"No known option, invoke following: '-v' for version , '-t' for testing , '-h' for help, '-l' for start listeining");
			log.info("Exiting with code 1");
			System.exit(1);
		} else {
			if (args[0].equalsIgnoreCase("-v")) {
				log.info("TKN_VERSION:{}", "1.0.0");
				System.exit(0);
			} else if (args[0].equalsIgnoreCase("-l")) {
				log.info("Runnig to listen tcp");
				listen = true;
			} else if (args[0].equalsIgnoreCase("-t")) {
				log.debug("TKN Unit testing");
				test = true;
			} else if (args[0].equalsIgnoreCase("-h")) {
				log.info("Application for listen to tcp packets");
				log.info("Usage:  '-v' for version , '-t' for testing , '-h' for help, '-l' for listen");
				log.info(
						"For specify application.properties use '--spring.config.location=<FULL_CONFIG_PATH_TO_YML_FILE>' as last command parameter");
				log.info(
						"Example: java -jar reply-server.jar -t --spring.config.location=/home/teknei/SITM/CONFIG/tkn_tcp_server.yml");
				System.exit(0);
			} else {
				log.info(
						"No known option, invoke following: '-v' for version , '-t' for testing , '-h' for help, '-l' for listen");
				log.info("Exiting with code 1");
				System.exit(1);
			}
		}
		ConfigurableApplicationContext ctx = SpringApplication.run(TknListenerTcpApplication.class, args);
		if (test) {
			log.info("Test....");
		} else if (listen) {
			System.out.println("##############################################");
			System.out.println("   ###########  ###     ###   #####     ###");
			System.out.println("       ###      ###   ####    ### ###   ###");
			System.out.println("       ###      ### #####     ###  ###  ###");
			System.out.println("       ###      ######        ###   ### ###");
			System.out.println("       ###      ###  ####     ###    ######");
			System.out.println("       ###      ####  ####    ###     #####");
			System.out.println("##############################################");
			System.out.println("TKN service started");
		}
	}
}
