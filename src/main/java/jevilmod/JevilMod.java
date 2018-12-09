package jevilmod;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import jevilmod.cards.JevilDefend;
import jevilmod.cards.JevilStrike;
import jevilmod.relics.Carousel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@SpireInitializer
public class JevilMod implements EditCharactersSubscriber,
        EditRelicsSubscriber, EditCardsSubscriber, PostInitializeSubscriber, EditStringsSubscriber {

    public static final Logger logger = LogManager.getLogger(JevilMod.class.getName());

    private static final String MOD_NAME = "The Jester";
    private static final String AUTHOR = "mezzoEmrys";
    private static final String DESCRIPTION = "A class for when you want a little chaos, chaos.";

    public static void initialize(){

        new JevilMod();
    }

    public JevilMod(){
        BaseMod.subscribe(this);
    }

    @Override
    public void receiveEditCharacters() {

        BaseMod.addCharacter(new Jevil(CardCrawlGame.playerName),
                "jevil_images/char/jesterButton.png",
                "jevil_images/char/portrait.png",
                JevilEnum.JEVIL_CLASS);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new Carousel(), RelicType.SHARED);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new JevilStrike());
        BaseMod.addCard(new JevilDefend());
    }

    @Override
    public void receivePostInitialize() {
        this.loadAudio();
    }

    private void loadAudio() {
        HashMap<String, Sfx> map = (HashMap<String, Sfx>) ReflectionHacks.getPrivate(CardCrawlGame.sound, SoundMaster.class, "map");
        map.put("JEVIL-CHAOS", new Sfx("jevil_audio/snd_joker_chaos.wav", false));
        map.put("JEVIL-ANYTHING", new Sfx("jevil_audio/snd_joker_anything.wav", false));
        map.put("JEVIL-LAUGH", new Sfx("jevil_audio/snd_joker_laugh0.wav", false));
    }


    @Override
    public void receiveEditStrings() {
        String relicStrings = Gdx.files.internal("localization/jevilRelics.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
    }
}
