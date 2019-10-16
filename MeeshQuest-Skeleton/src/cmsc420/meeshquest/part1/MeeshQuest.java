package cmsc420.meeshquest.part1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cmsc420.xml.XmlUtility;
/**
 * A skeletal version of the MeeshQuest program. This does the following: (1)
 * opens the input/output files; (2) validates and parses the xml input; (3)
 * iterates through the command nodes of the xml input, but doesn't do any
 * processing; (4) prints the results.
 */
public class MeeshQuest {

// --------------------------------------------------------------------------------------------
//  Uncomment these to read from standard input and output (USE THESE FOR YOUR FINAL SUBMISSION)
	private static final boolean USE_STD_IO = true; 
	private static String inputFileName = "";
	private static String outputFileName = "";
// --------------------------------------------------------------------------------------------
//  Uncomment these to read from a file (USE THESE FOR YOUR TESTING ONLY)
	/*private static final boolean USE_STD_IO = false;
	private static String inputFileName = "tests/part1/part1.test0.input.xml";
	private static String outputFileName = "tests/part1/part1.test0.output.xml";*/
// --------------------------------------------------------------------------------------------


	
/*	private void processCMD(Element cmd) {
		String name = cmd.getNodeName();
		if (name.equals("createCity")) {
			Commands.pcreatecity(cmd);
		}
		else if (name.equals("listCities")) {
			Commands.plistcities(cmd);
		}
	}*/
	private final static Commands commands = new Commands();
	public static void main(String[] args) {
		
		// configure to read from file rather than standard input/output
		if (!USE_STD_IO) {
			try {
				System.setIn(new FileInputStream(inputFileName));
				System.setOut(new PrintStream(outputFileName));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}

		// results will be stored here
		Document results = null;

		try {
			// validate and parse XML input
			Document input = XmlUtility.validateNoNamespace(System.in);
			// general XML document for results
			results = XmlUtility.getDocumentBuilder().newDocument();
			
			commands.setResults(results);
			// get input document root node
			Element rootNode = input.getDocumentElement();
			// get list of all nodes in document
			final NodeList nl = rootNode.getChildNodes();
			// enumerate through the commands
			for (int i = 0; i < nl.getLength(); i++) {
				// process only commands (ignore comments)
				if (nl.item(i).getNodeType() == Document.ELEMENT_NODE) {
					// get next command to process
					Element command = (Element) nl.item(i); // (ignore warning - just a skeleton)
					
					// ---------------------------------------
					// TODO: Add your command processing here
					// ---------------------------------------
					
					String name = command.getNodeName();
					if (name.equals("createCity")) {
						commands.pcreatecity(command);
					}
					else if (name.equals("listCities")) {
						commands.plistcities(command);
					}
					
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {

			// -----------------------------------------------------------
			// TODO: Add your error processing here
			// -----------------------------------------------------------

		} finally {
			try {
				// print the contents of the your results document
				XmlUtility.print(results);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	

}
