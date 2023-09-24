package com.axreng.backend.domain.model;

import org.junit.jupiter.api.Test;

import com.axreng.backend.domain.exceptions.DomainException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KeywordTest {

	@Test
	void testCreateValidKeyword() {
		// Arrange
		String validKeyword = "validKeyword";

		// Act
		Keyword keyword = new Keyword(validKeyword);

		// Assert
		assertEquals(validKeyword, keyword.getValue());
	}

	@Test
	void testShortKeyword() {
		// Arrange
		String shortKeyword = "xyz";

		// Act & Assert
		assertThrows(DomainException.class, () -> {
			new Keyword(shortKeyword);
		});
	}

	@Test
	void testLongKeyword() {
		// Arrange
		String longKeyword = "VeryLoooooooooooooooooooooooooongKeyWord";

		// Act & Assert
		assertThrows(DomainException.class, () -> {
			new Keyword(longKeyword);
		});
	}
}
