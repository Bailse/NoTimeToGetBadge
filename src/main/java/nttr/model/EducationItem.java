package nttr.model;

/**
 * Education item: increases skill gained from studying.
 */
public class EducationItem extends Item {
    private final double studyBoost;

    public EducationItem(String name, String imagePath, double studyBoost) {
        super(name, imagePath, ItemType.EDUCATION, "Study efficiency → gain more skill.");
        this.studyBoost = studyBoost;
    }

    @Override
    public void applyTo(Player player) {
        player.setStudySkillMultiplier(player.getStudySkillMultiplier() * studyBoost);
    }
}
