package it.polimi.jasper.engine.query.execution;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.reasoning.GenericRuleJenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.IdentityTVGReasoner;
import it.polimi.jasper.engine.reasoning.JenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.pellet.TVGReasonerPellet;
import it.polimi.jasper.engine.sds.JenaSDS;
import it.polimi.yasper.core.engine.Entailment;
import it.polimi.yasper.core.enums.EntailmentType;
import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.query.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by riccardo on 04/07/2017.
 */
public final class ContinuousQueryExecutionFactory extends QueryExecutionFactory {


    static public ContinuousQueryExecution create(RSPQuery query, JenaSDS sds, TVGReasoner r) {
        ContinuousQueryExecution cqe;
        StreamOperator r2S = query.getR2S()!= null ? query.getR2S() : StreamOperator.RSTREAM;
        RelationToStreamOperator s2r;
        switch (r2S) {
            case DSTREAM:
                s2r = RelationToStreamOperator.DSTREAM.get();
                break;
            case ISTREAM:
                s2r = RelationToStreamOperator.ISTREAM.get();
                break;
            case RSTREAM:
            default:
                s2r = RelationToStreamOperator.RSTREAM.get();
                break;
        }

        if (query.isSelectType()) {
            cqe = new ContinuousSelect(query, query.getQ(), sds, r, s2r);
        } else if (query.isConstructType()) {
            cqe = new ContinuouConstruct(query, query.getQ(), sds, r, s2r);
        } else {
            throw new RuntimeException("Unsupported ContinuousQuery Type [" + query.getQueryType() + "]");
        }
        return cqe;
    }

    public static JenaTVGReasoner getGenericRuleReasoner(Entailment e, Model tbox) {
        JenaTVGReasoner reasoner = null;
        EntailmentType ent = e.getType();
        switch (ent) {
            case OWL2DL:
                break;
            case OWL2EL:
                break;
            case OWL2QL:
                break;
            case OWL2RL:
                break;
            case PELLET:
                reasoner = new TVGReasonerPellet();
                reasoner.bindSchema(tbox);
                break;
            case RDFS:
            case RHODF:
            case CUSTOM:
            default:
                reasoner = getTvgReasoner(tbox, (List<Rule>) e.getRules());
        }
        return reasoner;

    }

    private static JenaTVGReasoner getTvgReasoner(Model tbox, List<Rule> rules) {
        GenericRuleJenaTVGReasoner reasoner = new GenericRuleJenaTVGReasoner(rules);
        reasoner.setMode(GenericRuleReasoner.HYBRID);
        return (GenericRuleJenaTVGReasoner) reasoner.bindSchema(tbox);
    }

    public static JenaTVGReasoner emptyReasoner() {
        return getTvgReasoner(ModelFactory.createDefaultModel(), new ArrayList<>());
    }
}
