package tech.mooki;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CashCardJsonTest {

	@Autowired
	private JacksonTester<CashCard> json;

	@Test
	void cashCardJsonSerialization() throws IOException {

		var data = new ClassPathResource("expected.json");

		CashCard cashCard = new CashCard(99L, 123.45);

		// Vérifie que le JSON sérialisé correspond exactement au fichier JSON attendu
		assertThat(json.write(cashCard)).isStrictlyEqualToJson(data);

		// Vérifie les valeurs spécifiques dans le JSON sérialisé
		assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
		assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id").isEqualTo(99);
		assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
		assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);

	}



	@Test
	void cashCardJsonDeserialization() throws IOException {
		// Charger le fichier JSON
		var data = new ClassPathResource("expected.json").getInputStream();

		String jsonContent = new String(data.readAllBytes(), StandardCharsets.UTF_8);


		// Désérialiser le JSON en objet CashCard
		CashCard expectedCashCard = new CashCard(99L, 123.45);
		CashCard actualCashCard = json.parseObject(jsonContent);

		// Comparer les objets désérialisés
		assertThat(actualCashCard).isEqualTo(expectedCashCard);

		// Comparer des valeurs spécifiques des objets désérialisés
		assertThat(actualCashCard.id()).isEqualTo(99L);
		assertThat(actualCashCard.amount()).isEqualTo(123.45);
	}


}
