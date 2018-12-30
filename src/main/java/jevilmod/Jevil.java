package jevilmod;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import static jevilmod.MezUtils.repeat;

public class Jevil extends CustomPlayer {

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int HAND_SIZE = 5;
    public static final int ORB_SLOTS = 3;

    public static final String JEVIL_SHOULDER_2 = "jevil_images/char/shoulder2.png"; // campfire pose
    public static final String JEVIL_SHOULDER_1 = "jevil_images/char/shoulder.png"; // another campfire pose
    public static final String JEVIL_CORPSE = "jevil_images/char/corpse.png"; // dead corpse
    public static final String JEVIL_ANIMATION = "jevil_images/char/jevil/jevil.scml"; // spriter animation file


    public Jevil(String name){
        super(name, JevilEnum.JEVIL_CLASS, null, "jevil_images/char/orb/vfx2.png", new SpriterAnimation(JEVIL_ANIMATION));

        initializeClass(null, JEVIL_SHOULDER_2, JEVIL_SHOULDER_1, JEVIL_CORPSE,
                getLoadout(), 20.0F, -20.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        this.energyOrb = new EnergyOrbJevil();
    }

    @Override
    public void updateOrb(int energy){
        this.energyOrb.updateOrb(energy);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Metamorphosis");
        list.add("Chrysalis");
        list.add("Chaos");
        repeat(4, i-> list.add("Strike_J"));
        repeat(4, i-> list.add("Defend_J"));
        return list;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Carousel");
        UnlockTracker.markRelicAsSeen("Carousel");
        return list;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(this.getLocalizedCharacterName(),
                "A mad jester who can do anything.",
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, HAND_SIZE,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) { return this.getLocalizedCharacterName(); }

    @Override
    public String getLocalizedCharacterName(){ return "The Jester"; }

    @Override
    public AbstractCard.CardColor getCardColor() { return AbstractCard.CardColor.COLORLESS;}

    @Override
    public Color getCardRenderColor() { return Color.LIGHT_GRAY;}

    @Override
    public AbstractCard getStartCardForEvent() { return new Madness(); }

    @Override
    public Color getCardTrailColor(){ return Color.LIGHT_GRAY;}

    @Override
    public int getAscensionMaxHPLoss() { return 4;}

    @Override
    public BitmapFont getEnergyNumFont() { return FontHelper.energyNumFontBlue;}

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playV("JEVIL-ANYTHING", 1);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() { return "JEVIL_LAUGH";}

    @Override
    public AbstractPlayer newInstance() { return new Jevil(this.name); }

    @Override
    public String getSpireHeartText() { return "Get ready for the big trick, boisengirls!";}

    @Override
    public Color getSlashAttackColor() { return Color.LIGHT_GRAY; }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.POISON, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SMASH};
    }

    @Override
    public String getVampireText() {
        return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[0];
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> cards){
        CardLibrary.cards.values().stream()
                .filter(card -> card.type != AbstractCard.CardType.CURSE)
                .filter(card -> card.type != AbstractCard.CardType.STATUS)
                .filter(card -> card.rarity != AbstractCard.CardRarity.BASIC)
                .forEach(cards::add);
        return cards;
    }

}
