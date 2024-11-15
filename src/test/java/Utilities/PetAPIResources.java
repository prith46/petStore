package Utilities;

public enum PetAPIResources {
    AddPet("/pet"),
    GetPetById("/pet/{petId}"),
    DeletePet("/pet/{petId}"),
    GetPetByStatus("/pet/findByStatus"),
    UploadPetImage("/pet/{petId}/uploadImage");

    private String resource;

    PetAPIResources(String resource){
        this.resource = resource;
    }

    public String getResource(){
        return resource;
    }
}
