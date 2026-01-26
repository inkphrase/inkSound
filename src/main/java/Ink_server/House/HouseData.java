package Ink_server.House;

import com.google.gson.annotations.SerializedName;
import java.util.*;

public class HouseData {
    @SerializedName("members")
    private final Set<UUID> members = new HashSet<>();

    @SerializedName("farm_level")
    private int farmLevel = 0;

    @SerializedName("farm_amount")
    private int farmAmount = 0;

    @SerializedName("pets")
    private final List<String> pets = new ArrayList<>();

    // Getters
    public Set<UUID> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public int getFarmLevel() {
        return farmLevel;
    }

    public int getFarmAmount() {
        return farmAmount;
    }

    public List<String> getPets() {
        return Collections.unmodifiableList(pets);
    }

    // Methods
    public void addMember(UUID uuid) {
        members.add(uuid);
    }

    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }

    public void addFarmLevel() {
        farmLevel += 1;
    }

    public void addFarmAmount(int amount) {
        farmAmount += amount;
    }

    public boolean addPet(String id) {
        if (pets.size() >= 4) return false;
        pets.add(id);
        return true;
    }

    public void updatePet(int num, String id) {
        pets.set(num, id);
    }
}