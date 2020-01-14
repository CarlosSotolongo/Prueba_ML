/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.PruebaMC.Utils;

import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class Translator {

	final String alphabet[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
			"r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " ", "." };
	final String morseCode[] = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..",
			"--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", "-----",
			".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "|", ".-.-.-" };
	HashMap<String, String> morseCodes = new HashMap<String, String>();
	HashMap<String, String> characters = new HashMap<String, String>();

	//El constructor llena los dos maps con los equivalentes morse a lketra, y letra a morse.
	public Translator() {
		for (int i = 0; i < alphabet.length; i++) {
			morseCodes.put(alphabet[i], morseCode[i]);
		}
		;
		for (int i = 0; i < alphabet.length; i++) {
			characters.put(morseCode[i], alphabet[i]);
		}
		;
	}

	//Como no hay forma exacta de saber cuantos 1s y 0s representan puntos, rayas y espacios entre palabras y letras, se saca
	//un promedio entre las dos para tener un aproximado
	public String decodeBits2Morse(String binarySecuence) {
		StringBuilder sb = new StringBuilder();
		String cleanedBinarySecuence = removeExcessCeros(binarySecuence);
		Double average1Secuence = calculateCharacterAverageAverage(cleanedBinarySecuence, '1');
		Double average0Secuence = calculateCharacterAverageAverage(cleanedBinarySecuence, '0');
		boolean firstIteration = true;
		String binaryRepresentation = "";
		char c = 0;
		for (int i = 0, n = cleanedBinarySecuence.length(); i < n; i++) {
			if (firstIteration) {
				c = cleanedBinarySecuence.charAt(i);
				firstIteration = false;
				binaryRepresentation += c;
				continue;
			} else {
				//Validacion para verificar si se llego al final de la cadena, como no hay mas bist que 
				//comparar mas adelante, se hace aca
				if(i == cleanedBinarySecuence.length()-1) {
					binaryRepresentation += cleanedBinarySecuence.charAt(i);
					if(c == '1') {
						if(binaryRepresentation.length() < average1Secuence)
							sb.append(".");
						else
							sb.append("-");
					} else {
						if(binaryRepresentation.length() < average0Secuence)
							sb.append("");
						else
							sb.append(" ");
					}
					break;
				}
				
				//Se valida que se sigue usando el mismo caracter que lo que se lleva de la sucesion actual
				if (c == cleanedBinarySecuence.charAt(i)) {
					binaryRepresentation += cleanedBinarySecuence.charAt(i);
				} else {
					if(c == '1') {
						if(binaryRepresentation.length() < average1Secuence)
							sb.append(".");
						else
							sb.append("-");
					} else {
						if(binaryRepresentation.length() < average0Secuence)
							sb.append("");
						else
							sb.append(" ");
					}
					binaryRepresentation = "";
					c = cleanedBinarySecuence.charAt(i);
					binaryRepresentation += c;
				}
			}
		}

		return sb.toString();
	}

	//Usando el mapa que contene de id los cogigos morse, se itera el mensaje en morse y se busca su equivalente en humano
	public String translate2Human(String morseSecuence) {
		StringBuilder sb = new StringBuilder();
		//Estas conversiones en el codigo morse se usan para facilitar saber cuando es espacio de palabra y cuadno es espacio 
		//de letra, esto ayuda para hacer el split correctamente
		String arrangeMorseSequence = morseSecuence.trim().replaceAll("  +", " | ");
		String[] morseArray = arrangeMorseSequence.split(" ");
		for (String morse : morseArray) {
			sb.append(characters.get(morse));
		}
		return sb.toString();
	}

	
	//Convierte texto normal en morse. Se busca agregar espacios simples para separar letras y espacios dobles para separar palabras
	public String humanToMorse(String humanSecuence) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < humanSecuence.length(); i++) {
			char c = humanSecuence.charAt(i);
			if (String.valueOf(c).equals(" ")) {
				sb.append("  ");
			} else {
				//Despues de cada letra en morse, se agrega un espacio simple para separarlo del proximo caracter en morse
				sb.append(morseCodes.get(String.valueOf(c))).append(" ");
			}
		}
		return sb.toString();
	}

	private Double calculateCharacterAverageAverage(String binarySecuence, char character) {
		double biggestSecuence = 0;
		double smallestSecuence = 999999;
		int currentCount = 0;

		for (int i = 0; i < binarySecuence.length(); i++) {
			char c = binarySecuence.charAt(i);
			
			if (character == c) {
				currentCount++;
			} else {
				if(currentCount > 0) {
					if (biggestSecuence < currentCount) {
						biggestSecuence = currentCount;
					}
					if (smallestSecuence > currentCount) {
						smallestSecuence = currentCount;
					}
					currentCount = 0;
				}
			}
		}
		
		Double average = (biggestSecuence + smallestSecuence)/2;
		return average;
	}
	
	private String removeExcessCeros(String baseBinaryCode) {
		try {
			int first1Location = 0;
			int last1Location = 0;
			for (int i = 0, n = baseBinaryCode.length(); i < n; i++) {
				char c = baseBinaryCode.charAt(i);
				if ('1' == c) {
					if(first1Location == 0)
						first1Location = i;
					else {
						last1Location = i;
					}
				}
			}
			return baseBinaryCode.substring(first1Location, last1Location+1);
		}catch (Exception e) {
			return baseBinaryCode;
		}
		
	}

}
