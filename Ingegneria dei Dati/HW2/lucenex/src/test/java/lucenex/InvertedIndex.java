package lucenex;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.LMJelinekMercerSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.tests.analysis.TokenStreamToDot;
import org.junit.Test;

public class InvertedIndex {




        //StopWords List
        public static final CharArraySet STOP_WORDS = new CharArraySet(Arrays.asList(
                "in", "dei", "di", "un", "una", "e", "il", "la", "in",
                "su", "di", "per", "con", "della", "le", "loro", "da",
                "a", "che", "del", "era", "mentre"), true);

        @Test
        public void testIndexStatistics() throws Exception {
            Path path = Paths.get("target/idx0");

            try (Directory directory = FSDirectory.open(path)) {
                indexDocs(directory, new SimpleTextCodec());
                try (IndexReader reader = DirectoryReader.open(directory)) {
                    IndexSearcher searcher = new IndexSearcher(reader);
                    Collection<String> indexedFields = FieldInfos.getIndexedFields(reader);
                    for (String field : indexedFields) {
                        System.out.println(searcher.collectionStatistics(field));
                    }
                } finally {
                    directory.close();
                }

            }
        }


        @Test
        public void testIndexingAndSearchAll() throws Exception {
            Path path = Paths.get("target/idx3");

            Query query = new MatchAllDocsQuery();

            try (Directory directory = FSDirectory.open(path)) {
                indexDocs(directory, null);
                try (IndexReader reader = DirectoryReader.open(directory)) {
                    IndexSearcher searcher = new IndexSearcher(reader);
                    runQuery(searcher, query);
                } finally {
                    directory.close();
                }

            }
        }

    @Test
    public void testIndexingAndSearchQP() throws Exception {
        Path path = Paths.get("target/idx1");

        QueryParser parser = new QueryParser("contenuto", new WhitespaceAnalyzer());
        Query query = parser.parse("+storia di +israele");

        try (Directory directory = FSDirectory.open(path)) {
            indexDocs(directory, null);
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                runQuery(searcher, query);
            } finally {
                directory.close();
            }

        }
    }
        @Test
        public void testIndexingAndSearchTQ() throws Exception {
            Path path = Paths.get("target/idx2");

            Query query = new TermQuery(new Term("contenuto", "persone"));

            try (Directory directory = FSDirectory.open(path)) {
                indexDocs(directory, null);
                try (IndexReader reader = DirectoryReader.open(directory)) {
                    IndexSearcher searcher = new IndexSearcher(reader);
                    runQuery(searcher, query);
                } finally {
                    directory.close();
                }

            }
        }


        @Test
        public void testIndexingAndSearchPQ() throws Exception {
            Path path = Paths.get("target/idx4");

            PhraseQuery query = new PhraseQuery.Builder()
                    .add(new Term("contenuto", "opere"))
                    .add(new Term("contenuto", "d'arte"))
                    .build();

            try (Directory directory = FSDirectory.open(path)) {
                indexDocs(directory, null);
                try (IndexReader reader = DirectoryReader.open(directory)) {
                    IndexSearcher searcher = new IndexSearcher(reader);
                    runQuery(searcher, query);
                } finally {
                    directory.close();
                }

            }
        }

        @Test
        public void testIndexingAndSearchPQ2() throws Exception {
            Path path = Paths.get("target/idx4");

            PhraseQuery query = new PhraseQuery(2, "contenuto", "pace", "terra");

            try (Directory directory = FSDirectory.open(path)) {
                indexDocs(directory, null);
                try (IndexReader reader = DirectoryReader.open(directory)) {
                    IndexSearcher searcher = new IndexSearcher(reader);
                    runQuery(searcher, query);
                } finally {
                    directory.close();
                }

            }
        }

        @Test
        public void testIndexingAndSearchPQWithSlop() throws Exception {
            Path path = Paths.get("target/idx4");

            PhraseQuery query = new PhraseQuery(1, "contenuto", "fama", "statua");

            try (Directory directory = FSDirectory.open(path)) {
                indexDocs(directory, null);
                try (IndexReader reader = DirectoryReader.open(directory)) {
                    IndexSearcher searcher = new IndexSearcher(reader);
                    runQuery(searcher, query);
                } finally {
                    directory.close();
                }

            }
        }

        @Test
        public void testIndexingAndSearchBQ() throws Exception {
            Path path = Paths.get("target/idx5");

            PhraseQuery phraseQuery = new PhraseQuery.Builder()
                    .add(new Term("contenuto", "lieto"))
                    .add(new Term("contenuto", "fine"))
                    .build();

            TermQuery termQuery = new TermQuery(new Term("titolo", "zeus"));

            BooleanQuery query = new BooleanQuery.Builder()
                    .add(new BooleanClause(termQuery, BooleanClause.Occur.SHOULD))
                    .add(new BooleanClause(phraseQuery, BooleanClause.Occur.SHOULD))
                    .build();

            try (Directory directory = FSDirectory.open(path)) {
                indexDocs(directory, null);
                try (IndexReader reader = DirectoryReader.open(directory)) {
                    IndexSearcher searcher = new IndexSearcher(reader);
                    runQuery(searcher, query);
                } finally {
                    directory.close();
                }

            }
        }

        @Test
        public void testIndexingAndSearchAllWithCodec() throws Exception {
            Path path = Paths.get("target/idx6");

            Query query = new MatchAllDocsQuery();

            try (Directory directory = FSDirectory.open(path)) {
                indexDocs(directory, new SimpleTextCodec());
                try (IndexReader reader = DirectoryReader.open(directory)) {
                    IndexSearcher searcher = new IndexSearcher(reader);
                    runQuery(searcher, query);
                } finally {
                    directory.close();
                }

            }
        }

    @Test
    public void testIndexingAndSearchQP1() throws Exception {
        Path path = Paths.get("target/idx7");

        QueryParser parser = new QueryParser("contenuto", new WhitespaceAnalyzer());
        Query query = parser.parse("confini di +olimpia");

        try (Directory directory = FSDirectory.open(path)) {
            indexDocs(directory, null);
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                runQuery(searcher, query);
            } finally {
                directory.close();
            }

        }
    }
    @Test
    public void testIndexingAndSearchQP2() throws Exception {
        Path path = Paths.get("target/idx8");

        QueryParser parser = new QueryParser("contenuto", new WhitespaceAnalyzer());
        Query query = parser.parse("giorni +antichi");

        try (Directory directory = FSDirectory.open(path)) {
            indexDocs(directory, null);
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                runQuery(searcher, query);
            } finally {
                directory.close();
            }

        }
    }
    @Test
    public void testIndexingAndSearchQP3() throws Exception {
        Path path = Paths.get("target/idx9");

        QueryParser parser = new QueryParser("contenuto", new WhitespaceAnalyzer());
        Query query = parser.parse("+arrende mai");

        try (Directory directory = FSDirectory.open(path)) {
            indexDocs(directory, null);
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                runQuery(searcher, query);
            } finally {
                directory.close();
            }

        }
    }
    @Test
    public void testIndexingAndSearchQP4() throws Exception {
        Path path = Paths.get("target/idx10");

        QueryParser parser = new QueryParser("contenuto", new WhitespaceAnalyzer());
        Query query = parser.parse("+storia statua nazione palestina");

        try (Directory directory = FSDirectory.open(path)) {
            indexDocs(directory, null);
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                runQuery(searcher, query);
            } finally {
                directory.close();
            }

        }
    }


        private void runQuery(IndexSearcher searcher, Query query) throws IOException {
            runQuery(searcher, query, false);
        }

        private void runQuery(IndexSearcher searcher, Query query, boolean explain) throws IOException {
            TopDocs hits = searcher.search(query, 10);
            for (int i = 0; i < hits.scoreDocs.length; i++) {
                ScoreDoc scoreDoc = hits.scoreDocs[i];
                Document doc = searcher.doc(scoreDoc.doc);
                System.out.println("doc"+scoreDoc.doc + ":"+ doc.get("titolo") + " (" + scoreDoc.score +")");
                if (explain) {
                    Explanation explanation = searcher.explain(query, scoreDoc.doc);
                    System.out.println(explanation);
                }
            }
        }

        private void indexDocs(Directory directory, Codec codec) throws IOException {
            Analyzer defaultAnalyzer = new StandardAnalyzer();

            Map<String, Analyzer> perFieldAnalyzers = new HashMap<>();
            //Ho provato ad utilizzare l'ItalianAnalyzer ma non tokenizzava correttamente
            // rimuovendo l'ultima lettera dalla parola se presente nella lista delle stop word italiane
            perFieldAnalyzers.put("contenuto", new StandardAnalyzer(STOP_WORDS));
            //I've done this choice for the title with a custom Analyzer
            perFieldAnalyzers.put("titolo", new StandardAnalyzer(STOP_WORDS));

            Analyzer analyzer = new PerFieldAnalyzerWrapper(defaultAnalyzer, perFieldAnalyzers);
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            if (codec != null) {
                config.setCodec(codec);
            }
            IndexWriter writer = new IndexWriter(directory, config);
            writer.deleteAll();

            //Storia della Palestina
            Document doc1 = new Document();
            doc1.add(new TextField("titolo", "Storia della Palestina", Field.Store.YES));
            doc1.add(new TextField("contenuto", LeggiContenutoDoc("Storia della Palestina.txt"), Field.Store.YES));

            //Cenere e Promessa
            Document doc2 = new Document();
            doc2.add(new TextField("titolo", "Cenere e Promessa, Una Storia di Rinascita", Field.Store.YES));
            doc2.add(new TextField("contenuto", LeggiContenutoDoc("Cenere e promessa Una Storia Di Rinascita.txt"), Field.Store.YES));

            //Il Trono Divino
            Document doc3 = new Document();
            doc3.add(new TextField("titolo", "Il Trono Divino, La Statua di Zeus a Olimpia", Field.Store.YES));
            doc3.add(new TextField("contenuto", LeggiContenutoDoc("Il Trono Divino La Statua di Zeus a Olimpia.txt"), Field.Store.YES));

            writer.addDocument(doc1);
            writer.addDocument(doc2);
            writer.addDocument(doc3);

            writer.commit();
            writer.close();
        }

        /*Legge il Contenuto del Documento passato in Input*/
        public String LeggiContenutoDoc(String nomeDocumento){
            try {
                return new String(Files.readAllBytes(Paths.get(nomeDocumento)));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Test
        public void testAnalyzer() throws Exception {
            Analyzer a = new StandardAnalyzer(lucenex.SamplesTest.STOP_WORDS);
            TokenStream ts = a.tokenStream(null, "Come diventare un ingegnere dei dati,");
            StringWriter w = new StringWriter();
            new TokenStreamToDot(null, ts, new PrintWriter(w)).toDot();
            System.out.println(w);
        }

}

