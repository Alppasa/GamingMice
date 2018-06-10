
package net.semwebprogramming.chapter2.HelloSemanticWeb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mindswap.pellet.jena.PelletReasonerFactory;


import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;


public class OntologyQueries {

	public static void main(String[] args) throws IOException {
		// Open the bloggers RDF graph from the filesystem
		InputStream in = new FileInputStream(new File("Ontologies/gaming_mouse.owl"));
		 
		// Create an empty in-memory model and populate it from the graph
		Model model = ModelFactory.createOntologyModel();
		model.read(in,null);
		in.close();
		
		// Create an empty in-memory model and populate it from the graph
//		Model model = ModelFactory.createMemModelMaker().createModel();
//		model.read(in,null); // null base URI, since model URIs are absolute
//		in.close();
		 
//		 Create a new query
//		String queryString = 
//		    "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
//		    "SELECT ?url " +
//		    "WHERE {" +
//		    "      ?contributor foaf:name \"Jon Foobar\" . " +
//		    "      ?contributor foaf:weblog ?url . " +
//		    "      }";
		
		List<String> queryStrings = new ArrayList<>();
		List<String> descriptions = new ArrayList<>();
		
		descriptions.add("Query all gaming mice that have a tunable weight.");
		queryStrings.add("PREFIX gm: <http://www.semanticweb.org/vince/ontologies/2018/4/untitled-ontology-8#> "
				+ "SELECT DISTINCT ?manufacturer ?name WHERE "
				+ "{?m gm:isTunable True . ?m gm:name ?name. ?m gm:manufacturer ?manufacturer}");
		
		descriptions.add("Query the logitech G903.");
		queryStrings.add("PREFIX gm: <http://www.semanticweb.org/vince/ontologies/2018/4/untitled-ontology-8#> "
				+ "SELECT DISTINCT ?model WHERE "
				+ "{?m gm:name \"G 903\" . ?m gm:name ?model }");
		
		descriptions.add("Query all gaming mice that have a sensor with a DPI > 10000.");
		queryStrings.add("PREFIX gm: <http://www.semanticweb.org/vince/ontologies/2018/4/untitled-ontology-8#> "
				+ "SELECT DISTINCT ?name ?sensor ?max_dpi WHERE "
				+ "{?m gm:name ?name. ?m gm:sensor ?sensor . ?m gm:maximumDPI ?max_dpi . FILTER (?max_dpi > 10000)} "
				+ "ORDER BY DESC(?max_dpi)");
		
		 
		for (int i = 0; i < queryStrings.size(); i++) {
			String description = descriptions.get(i);
			String q = queryStrings.get(i);
			Query query = QueryFactory.create(q);
			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			
			// Output query results 
			System.out.println(description);
			ResultSetFormatter.out(System.out, results, query);
			System.out.println("\n\n");
			
			// Important - free up resources used running the query
			qe.close();			
		}
		 
	}
}