import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;

import java.io.InputStream;

public class JenaBasicModel {
    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();

        String ontologyUri = "http://www.person.com/ontology/v1#";
        String hernanUri = ontologyUri + "Hernan";
        String personUri = ontologyUri + "Persona";

        Resource hernanResource = model.createResource(hernanUri);
        Resource personResource = model.createResource(personUri);

        hernanResource.addProperty(RDF.type, personResource);

        // Blank node

        hernanResource.addProperty(VCARD.FN,
                    model.createResource()
                        .addProperty(VCARD.Given,"Hernan")
                        .addProperty(VCARD.Family, "Vanegas")
                );

        // Import model from file

        InputStream fileOwl = FileManager.get().open("src/ontologies/libros.owl");
        Model modelFromFile = ModelFactory.createDefaultModel();
        modelFromFile.read(fileOwl, null, "Turtle");

        Reasoner owlReasoner = ReasonerRegistry.getOWLReasoner();
        InfModel infModel = ModelFactory.createInfModel(owlReasoner, modelFromFile);

        ValidityReport validityReport = infModel.validate();
        boolean showStatements = false;
        System.out.println("========= FROM ONTOLOGY FILE ========");
        if(validityReport.isValid()) {
            System.out.println("Validation complete: VALID");

            StmtIterator iterator = infModel.listStatements();
            if (showStatements) {
                while (iterator.hasNext()) {
                    Statement statement = iterator.nextStatement();

                    Resource resource = statement.getSubject();
                    Resource property = statement.getPredicate();
                    RDFNode object = statement.getObject(); // I don't know if it's a literal or a Resource itself

                    System.out.println("---------------------------- STATEMENT ------------------------------");
                    System.out.println("Subject:" + resource);
                    System.out.println("Predicate: " + property);
                    System.out.println("Object: " + object);
                }
            }

            // Run some queries over infModel

            Query query = QueryFactory.create(QueryList.BOOKS_EDITOR_OCEANO);
            QueryExecution queryExecution = QueryExecutionFactory.create(query, infModel);

            ResultSet resultSet = queryExecution.execSelect();

            ResultSetFormatter.out(resultSet);
        } else {
            System.out.println("Validation complete: INVALID");
        }

        // Remote query to DBpedia

        Query queryDbpedia = QueryFactory.create(QueryList.AUTHOR_INFLUENCER_BOOKS_DBPEDIA);
        QueryExecution queryExecutionDbpedia = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", queryDbpedia);
        ResultSet resultSetDbpedia = queryExecutionDbpedia.execSelect();

        System.out.println("============= FROM DBPEDIA =============");

        ResultSetFormatter.out(resultSetDbpedia);
    }
}
