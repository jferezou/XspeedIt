package com.xspeedit.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xspeedit.exception.FichierException;
import com.xspeedit.service.FillService;
import com.xspeedit.service.ReaderFileService;

/**
 * Service perettant les opérations sur les fichiers : lecture/ecriture
 * @author jferezou
 *
 */
@Service
public class ReaderFileServiceImpl implements ReaderFileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReaderFileServiceImpl.class);

	@Value("${xspeedIt.fichier}")
	private String filePath;
	
	@Value("${xspeedIt.fichierResultat}")
	private String fichierResultat;

	@Autowired
	FillService fillService;

	/**
	 * Méthode lancant la lecture du fichier, l'optimisation et l'écriture des résultats
	 */
	@Override
	public void readAndLaunch() throws FichierException, TikaException, IOException {
		LOGGER.info("Début du traitement");
		// Vérifie que le fichier existe
		File file = new File(this.filePath);
		String fileName = file.getName();
		if (!file.exists()) {
			throw new FichierException("Ce fichier n'existe pas : " + this.filePath);
		}
		// Vérifie que ce n'est pas un répertoire
		else if (file.isDirectory()) {
			throw new FichierException("C'est un répertoire : " + this.filePath);
		}
		// verifie que ce soit bien un txt
		else {
			InputStream stream = new FileInputStream(file);
			InputStream bufferedInputstream = new BufferedInputStream(stream);
			MediaType fileInfo = this.getFileInfo(fileName, bufferedInputstream);
			if (!MediaType.TEXT_PLAIN.equals(fileInfo.getBaseType())) {
				throw new FichierException("Le fichier doit être au format " + MediaType.TEXT_PLAIN + " : " + this.filePath);
			}
			// si c'est ok on continue
			else {
				// on créé notre fichier de sortie
				this.multiThreaded(file);
			}
		}

	}

	/**
	 * Traitement multithread
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void multiThreaded(File file) throws IOException {
		List<String> list = Files.readAllLines(Paths.get(file.getPath()));
		List<String> lineResult = IntStream.range(0, list.size()).mapToObj(i -> this.fillService.fill(list.get(i))).collect(Collectors.toList());

		try (final FileWriter fw = new FileWriter(this.fichierResultat); final BufferedWriter bw = new BufferedWriter(fw);) {
			// on écrit les résultats dans le fichier
			for (String cpt : lineResult) {
				bw.append(cpt);
				bw.newLine();
			}
		}
	}

	/**
	 * Méthode utilisant apache Tika pour récupérer les infos du fichier :
	 * mimetype, charset ....
	 * 
	 * @param fileName
	 * @param stream
	 * @return
	 * @throws TikaException
	 * @throws IOException
	 */
	private MediaType getFileInfo(final String fileName, final InputStream stream) throws TikaException, IOException {
		TikaConfig tika = new TikaConfig();
		Metadata metadata = new Metadata();
		metadata.set(Metadata.RESOURCE_NAME_KEY, fileName);
		return tika.getDetector().detect(stream, metadata);
	}

}
