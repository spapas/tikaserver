package gr.serafeim;



import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;
        import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainServlet extends HttpServlet
{
    private AutoDetectParser parser = null;
    static Logger logger = Logger.getLogger(MainServlet.class.getName());

    public void init(ServletConfig config) throws ServletException {
        logger.log(Level.INFO, "Initializing MainServlet");

        super.init(config);
        TikaConfig tikaConfig = null;

        try {
            InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("tika-config.xml");
            tikaConfig = new TikaConfig(systemResourceAsStream);
            parser = new AutoDetectParser(tikaConfig);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        logger.log(Level.INFO, "Done initializing MainServlet");

    }
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<html><body><h1>Εξαγωγή κειμένου</h1><form method='POST' enctype='multipart/form-data'><input name='file' type='file'><input type='submit'></form></body><html>");
    }

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("file");

        String res = "Cannot parse";

        Metadata metadata = new Metadata();

        try {
            //res = tika.parseToString(filePart.getInputStream());
            ContentHandler handler = new ToXMLContentHandler();

            //TikaConfig tikaConfig = TikaConfig.getDefaultConfig();
            //TikaConfig tikaConfig = new TikaConfig("file.xml");



            parser.parse(filePart.getInputStream(), handler, metadata);
            res = handler.toString();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<html><body><a href='/'>Αρχή</a><h1>Αποτέλεσμα</h1>" + res + "<h3>Μεταπληροφορία</h3>" + metadata + "</body><html>");
    }


}