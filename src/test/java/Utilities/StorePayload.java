package Utilities;

public class StorePayload {

    public static String getValidStorePayload(String id, String petId, String quantity, String status, String complete){
        return "{\n" +
                "  \"id\": "+id+",\n" +
                "  \"petId\": "+petId+",\n" +
                "  \"quantity\": "+quantity+",\n" +
                "  \"shipDate\": \"2024-11-15T06:59:37.212Z\",\n" +
                "  \"status\": \""+status+"\",\n" +
                "  \"complete\": "+complete+"\n" +
                "}";
    }

    public static String getInvalidPayload(){
        return "{\n" +
                "  \"id\": 572,\n" +
                "  \"petId\": 2357,\n" +
                "  \"quantity\": 7,\n" +
                "  \"shipDate\": \"2024-11-15T06:59:37.212Z\",\n" +
                "  \"status\": ,\n" +
                "  \"complete\": true\n" +
                "}";
    }
}
