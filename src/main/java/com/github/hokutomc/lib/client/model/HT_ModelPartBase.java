package com.github.hokutomc.lib.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Using this class is easier way to describe the hierarchy of model parts.
 * This class has some convenient methods.
 * 2014/12/31.
 */
public class HT_ModelPartBase extends ModelRenderer implements HT_I_ExModelPart {

    private ArrayList<HT_I_AtomicModel> m_uniqueBoxes = new ArrayList<>();
    private int m_uniqueDisplayList;
    private boolean m_isUniqueCompiled;

    public HT_ModelPartBase (ModelBase base, ModelRenderer parent, String name) {
        super(base, name);
        if (parent != null) {
            parent.addChild(this);
        }

    }

    public HT_ModelPartBase (ModelBase base, ModelRenderer parent, int offsetX, int offsetY) {
        this(base, offsetX, offsetY);
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public HT_ModelPartBase (ModelBase base, String name) {
        super(base, name);
    }

    public HT_ModelPartBase (ModelBase base) {
        super(base);
    }

    public HT_ModelPartBase (ModelBase base, int offsetX, int offsetY) {
        super(base, offsetX, offsetY);
    }

    public void HT_setChildren (ModelRenderer... children) {
        for (ModelRenderer e : children) {
            this.addChild(e);
        }
    }

    public void setMirror () {
        this.mirror = true;
    }

    public HT_ModelPartBase addCube (float originX, float originY, float oroginZ, int sizeX, int sizeY, int sizeZ) {
        super.addBox(originX, originY, oroginZ, sizeX, sizeY, sizeZ);
        return this;
    }

    public HT_ModelPartBase addCube (float originX, float originY, float originZ, int sizeX, int sizeY, int sizeZ, float scale) {
        super.addBox(originX, originY, originZ, sizeX, sizeY, sizeZ, scale);
        return this;
    }

    public void HT_addAtomicBox (HT_I_AtomicModel atomicModel) {
        if (this.m_uniqueBoxes == null) {
            this.m_uniqueBoxes = new ArrayList<>();
        }
        this.m_uniqueBoxes.add(atomicModel);
    }



    public void syncRotateAngleX (ModelRenderer renderer) {
        HT_ModelUtil.syncRotateAngleX(this, renderer);
    }

    public void syncRotateAngleY (ModelRenderer renderer) {
        HT_ModelUtil.syncRotateAngleY(this, renderer);
    }

    public void syncRotateAngleZ (ModelRenderer renderer) {
        HT_ModelUtil.syncRotateAngleZ(this, renderer);
    }

    public void syncRotateAngleAll (ModelRenderer renderer) {
        HT_ModelUtil.syncRotateAngleAll(this, renderer);
    }

    public void unrotateX () {
        HT_ModelUtil.unrotateX(this);
    }

    public void unrotateY () {
        HT_ModelUtil.unrotateY(this);
    }

    public void unrotateZ () {
        HT_ModelUtil.unrotateZ(this);
    }

    @Override
    public void render (float p_78785_1_) {
        super.render(p_78785_1_);
        if (!this.isHidden && this.showModel && !this.m_isUniqueCompiled) {
            this.compileUniqueList(p_78785_1_);
        }
    }

    @Override
    public void renderWithRotation (float p_78791_1_) {
        super.renderWithRotation(p_78791_1_);
        if (!this.isHidden && this.showModel && !this.m_isUniqueCompiled) {
            this.compileUniqueList(p_78791_1_);
        }
    }

    @Override
    public void postRender (float p_78794_1_) {
        super.postRender(p_78794_1_);
        if (!this.isHidden && this.showModel && !this.m_isUniqueCompiled) {
            this.compileUniqueList(p_78794_1_);
        }
    }

    @Override
    public boolean isUniqueCompiled () {
        return this.m_isUniqueCompiled;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void compileUniqueList(float p_78788_1_) {
        this.m_uniqueDisplayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(this.m_uniqueDisplayList, GL11.GL_COMPILE);
        Tessellator tessellator = Tessellator.getInstance();

        for (HT_I_AtomicModel uniqueBox : this.m_uniqueBoxes) {
            uniqueBox.render(tessellator, p_78788_1_);
        }

        GL11.glEndList();
        this.m_isUniqueCompiled = true;
    }
}
