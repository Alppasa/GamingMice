
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
		queryStrings.add("PREFIX gm: <http://www.semanticweb.org/vince/ontologies/2018/4/untitled-ontology-8#> "
				+ "SELECT DISTINCT ?manufacturer ?name WHERE {?m gm:isTunable True . ?m gm:name ?name. ?m gm:manufacturer ?manufacturer}");
		queryStrings.add("PREFIX gm: <http://www.semanticweb.org/vince/ontologies/2018/4/untitled-ontology-8#> SELECT DISTINCT ?model WHERE {?m gm:name \"G 903\" . ?m gm:name ?model }");
		 
		for (String q: queryStrings) {
			Query query = QueryFactory.create(q);
			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			
			// Output query results 
			ResultSetFormatter.out(System.out, results, query);
			
			// Important - free up resources used running the query
			qe.close();			
		}
		 
	}
}