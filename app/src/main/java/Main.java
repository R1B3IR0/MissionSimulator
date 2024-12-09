import Game.Building;



public class Main {
    public static void main(String[] args) {
        String filePath = "app//src//main//resources//mission.json";
        Building building = new Building();
        building.generateMapFromJson(filePath);
        System.out.println(building.getMap().toString());
    }
}
