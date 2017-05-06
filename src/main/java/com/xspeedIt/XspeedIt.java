package com.xspeedIt;

import java.io.IOException;
import java.util.Date;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.xspeedIt.exception.TailleArticleException;
import com.xspeedIt.service.ReaderFileService;
import com.xspeedIt.service.impl.ReaderFileServiceImpl;

/**
 * Classe principale
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
		Date debut = new Date();
		LOGGER.info("Debut traitement");
		try {
			//chargement contexte spring
			ReaderFileService reader = context.getBean(ReaderFileServiceImpl.class);
			reader.readAndLaunch();
		}
		catch(TailleArticleException | TikaException | IOException e) {
			LOGGER.error("Erreur lors du traitement : ", e);
		}
		Date fin = new Date();
		LOGGER.info("Fin traitement en : {} ms", fin.getTime() - debut.getTime() );
		
	}
}
