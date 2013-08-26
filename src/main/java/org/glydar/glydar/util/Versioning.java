package org.glydar.glydar.util;

import org.glydar.paraglydar.ParaGlydar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Versioning {

	public static String getParaGlydarVersion() {
		String result = "Unknown-Version";

		InputStream stream = ParaGlydar.class.getResourceAsStream("/META-INF/maven/org.glydar.paraglydar/ParaGlydar/pom.properties");
		Properties properties = new Properties();

		if (stream != null) {
			try {
				properties.load(stream);

				result = properties.getProperty("version");
			} catch (IOException ex) {
				Logger.getLogger(Versioning.class.getName()).log(Level.SEVERE, "Could not get Glydar version!", ex);
			}
		}

		return result;
	}
}
