package kmeans;

public class ColorPicker {
    
    public static String get(int id) {
        String color = "";
        switch (id) {
        
            case 0: color = "black"; break;
            case 1: color = "red"; break;
            case 2: color = "yellow"; break;
            case 3: color = "green"; break;
            case 4: color = "blue"; break;
            case 5: color = "cyan"; break;
            case 6: color = "gray"; break;
            
        }
        
        return color;
    }
}
