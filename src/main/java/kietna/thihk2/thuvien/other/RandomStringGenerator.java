package kietna.thihk2.thuvien.other;

import java.nio.file.Path;
import java.util.Random;

public class RandomStringGenerator {
	public RandomStringGenerator() {};
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6;
    
    public static String gen() {
    	Random random = new Random();
    	StringBuilder stringBuilder = new StringBuilder(LENGTH);
    	
    	for(int i = 0; i < LENGTH; i++) {
    		int randomIndex = random.nextInt(CHARACTERS.length());
    		char randomChar = CHARACTERS.charAt(randomIndex);
    		stringBuilder.append(randomChar);
    	}
    	return stringBuilder.toString();
    }
    
    public static String getFileExtension(String fileName) {
    	Path path = Path.of(fileName);
    	String extension = "";
    	if(path.getFileName() != null) {
    		String fineNameStr = path.getFileName().toString();
    		int dotIndex = fineNameStr.lastIndexOf(".");
    		if(dotIndex != -1 && dotIndex < fineNameStr.length()) {
    			extension = fineNameStr.substring(dotIndex + 1);
    		}
    	}
    	return extension;
    }

}
