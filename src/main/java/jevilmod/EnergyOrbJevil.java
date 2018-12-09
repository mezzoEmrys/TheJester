package jevilmod;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;

public class EnergyOrbJevil extends CustomEnergyOrb {

    private float w = 0F;
    private float h = 0F;

    private static final float ORB_IMG_SCALE = 1.15F * Settings.scale;

    private float renderAngle = 0F;

    public static final ArrayList<Texture> energyActiveLayers = new ArrayList<>();
    public static final ArrayList<Texture> energyDisabledLayers = new ArrayList<>();

    private Texture defaultOrb = ImageMaster.loadImage("jevil_images/char/orb/vfx2.png");

    public EnergyOrbJevil(){
        super(null, null, null);
        this.energyActiveLayers.add(new Texture("jevil_images/char/orb/orb1a.png"));
        this.energyDisabledLayers.add(new Texture("jevil_images/char/orb/orb1d.png"));
        energyActiveLayers.forEach(t -> t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear));
        energyDisabledLayers.forEach(t -> t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear));
    }

    public void updateOrb(int energy)
    {
        if (energy > 0){
            this.renderAngle += Gdx.graphics.getDeltaTime() * 40.0F;
        } else {
            this.renderAngle += Gdx.graphics.getDeltaTime() * 10.0F;
        }
    }

    public void saneDraw(float current_x, float current_y, SpriteBatch sb, Texture tex, float xOffset, float yOffset, float rotation) {
        this.w = tex.getWidth();
        this.h = tex.getHeight();

        sb.draw(tex, current_x - (this.w/2.0F) + xOffset, current_y - (this.h/2.0F) + yOffset, this.w/2.0F, this.h/2.0F, this.w, this.h, ORB_IMG_SCALE, ORB_IMG_SCALE, rotation, 0, 0, (int)this.w, (int)this.h, false, false);
    }

    public Texture getEnergyImage()
    {
        return defaultOrb;
    }

    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y)
    {
        if (enabled)
        {
            sb.setColor(Color.WHITE);

            this.saneDraw(current_x, current_y, sb, this.energyActiveLayers.get(0), 0F, 0F, this.renderAngle);
        }
        else
        {
            sb.setColor(Color.WHITE);

            this.saneDraw(current_x, current_y, sb, this.energyDisabledLayers.get(0), 0F, 0F, this.renderAngle);
        }
    }
}
