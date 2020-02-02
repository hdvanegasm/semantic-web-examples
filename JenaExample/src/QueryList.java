public class QueryList {
    public static final String AUTHOR_INFLUENCER_BOOKS_DBPEDIA = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
            "\n" +
            "SELECT ?author ?influencer ?book\n" +
            "WHERE {\n" +
            "\t{\n" +
            "\t\tSELECT ?author ?influencer\n" +
            "\t\tWHERE {\n" +
            "\t\t\t?author rdf:type dbo:Writer .\n" +
            "\t\t\t?author dbo:influencedBy ?influencer .\n" +
            "\t\t\t?influencer rdf:type dbo:Writer .\n" +
            "\t\t}\n" +
            "\t}\n" +
            "\t?book dbo:author ?influencer .\n" +
            "}\n" +
            "LIMIT 20";

    public static final String BOOKS_EDITOR_OCEANO = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX onto: <http://www.books.com/ontology/v1#>\n" +
            "\n" +
            "SELECT ?titulo\n" +
            "WHERE\n" +
            "{\n" +
            "\t?book a onto:Libro .\n" +
            "\t?book onto:editado_por onto:Oceano .\n" +
            "\t?book onto:titulo ?titulo .\n" +
            "}\n";

}
