package com.github.hokutomc.lib.client.model;

import net.minecraft.client.model.ModelRenderer;

/**
 * This is util class.
 * 2014/12/31.
 */
public final class HT_ModelUtil {
    public static void syncRotateAngleX (ModelRenderer self, ModelRenderer renderer) {
        self.rotateAngleX = renderer.rotateAngleX;
    }

    public static void syncRotateAngleY (ModelRenderer self, ModelRenderer renderer) {
        self.rotateAngleY = renderer.rotateAngleY;
    }

    public static void syncRotateAngleZ (ModelRenderer self, ModelRenderer renderer) {
        self.rotateAngleZ = renderer.rotateAngleZ;
    }

    public static void syncRotateAngleAll (ModelRenderer self, ModelRenderer renderer) {
        syncRotateAngleX(self, renderer);
        syncRotateAngleY(self, renderer);
        syncRotateAngleZ(self, renderer);
    }

    public static void HT_compileUniqueModelList (ModelRenderer self, float p) {
        if (!self.isHidden && self.showModel && self instanceof HT_I_ExModelPart && !((HT_I_ExModelPart)self).isUniqueCompiled()) {
            ((HT_I_ExModelPart) self).compileUniqueList(p);
        }
    }

    public static void unrotateX (ModelRenderer self) {
        self.rotateAngleX = 0;
    }

    public static void unrotateY (ModelRenderer self) {
        self.rotateAngleY = 0;
    }

    public static void unrotateZ (ModelRenderer self) {
        self.rotateAngleZ = 0;
    }


}
