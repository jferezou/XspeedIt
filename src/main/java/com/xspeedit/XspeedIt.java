package com.xspeedit;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.xspeedit.exception.TailleArticleException;
import com.xspeedit.service.ReaderFileService;
import com.xspeedit.service.impl.ReaderFileServiceImpl;

/**
 * Classe principale lançant l'algo
 * @author jferezou
 *
 */
public class XspeedIt {
	private static final Logger LOGGER = LoggerFactory.getLogger(XspeedIt.class);

	private XspeedIt(){
		
	}
	
	public static void main(String[] args) {
		new SpringRunner(XspeedIt.class, AppConfig.class).run(args);
	}

	
	public void run(String[] args, AnnotationConfigApplicationContext context) {
		LOGGER.info("Debut traitement");
		Instant start = Instant.now();
		try {
			//chargement contexte spring
			ReaderFileService reader = context.getBean(ReaderFileServiceImpl.class);
			reader.readAndLaunch();
		}
		catch(TailleArticleException | TikaException | IOException e) {
			LOGGER.error("Erreur lors du traitement : ", e);
		}
		Instant end = Instant.now();
		LOGGER.info("Fin traitement en : {} ms", Duration.between(start, end).toMillis());
		
	}
}
