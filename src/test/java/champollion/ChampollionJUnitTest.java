package champollion;

import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ChampollionJUnitTest {
	Enseignant untel;
	UE uml, java;

	@BeforeEach
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		uml = new UE("UML");
		java = new UE("Programmation en java");
	}


	@Test
	public void testNouvelEnseignantSansService() {
		assertEquals(0, untel.heuresPrevues(),
                        "Un nouvel enseignant doit avoir 0 heures prévues");
	}

	@Test
	public void testAjouteHeures() {
                // 10h TD pour UML
		untel.ajouteEnseignement(uml, 0, 10, 0);

		assertEquals(10, untel.heuresPrevuesPourUE(uml),
                        "L'enseignant doit maintenant avoir 10 heures prévues pour l'UE 'uml'");

		// 20h TD pour UML
        untel.ajouteEnseignement(uml, 0, 20, 0);

		assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml),
                         "L'enseignant doit maintenant avoir 30 heures prévues pour l'UE 'uml'");

	}

    @Test
    public void testHeuresPrevuesUE() {
        UE math = new UE("Mathématiques");

        assertEquals(0, untel.heuresPrevuesPourUE(math),
            "L'enseignant maintenant avoir 0 heures prévues pour l'UE 'math'");
    }

    @Test
    public void testTotalHeuresPrevues() {
        untel.ajouteEnseignement(uml, 0, 10, 0);
        untel.ajouteEnseignement(java, 0, 20, 0);

        assertEquals(30, untel.heuresPrevues(),
            "L'enseignant maintenant avoir 0 heures prévues pour l'UE 'math'");
    }

    @Test
    public void testAjoutIntervention() throws ExceptionServiceDepasse {
        untel.ajouteEnseignement(uml, 0, 20, 0);
        try {
            untel.ajouteIntervention(new Intervention(new Salle("B10", 30), uml, new Date(), 30, TypeIntervention.TD));
            System.out.println(untel.interventions.get(uml).toString());
            fail("L'enseignant n'a pas dépassé son service");
        } catch (ExceptionServiceDepasse e) {

        }

    }
}
