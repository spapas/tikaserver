package gr.serafeim;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.*;


public class TestTika {
    public static void runme() {
        System.out.println("Runme");

        ContentHandler handler = new ToXMLContentHandler();

        //TikaConfig tikaConfig = TikaConfig.getDefaultConfig();

        //PDFParser parser = new PDFParser();

        Metadata metadata = new Metadata();
        InputStream fis = null ;
        try {
            TikaConfig tikaConfig = new TikaConfig("c:/progr/java/tikaserver/file.xml");
            AutoDetectParser parser = new AutoDetectParser(tikaConfig);

            fis = new FileInputStream("c:/progr/java/tika/kokokoko.pdf");
            //fis = new FileInputStream("c:/progr/java/tika/Untitled.png");
            parser.parse(fis, handler, metadata);
            System.out.println(handler.toString());
            System.out.println(metadata.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }
}
