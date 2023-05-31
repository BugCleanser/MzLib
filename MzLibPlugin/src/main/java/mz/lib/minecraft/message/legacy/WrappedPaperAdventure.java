package mz.lib.minecraft.message.legacy;

import mz.lib.Optional;
import mz.lib.wrapper.WrappedClass;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

import java.util.Locale;

@Optional
@WrappedClass("io.papermc.paper.adventure.PaperAdventure")
public interface WrappedPaperAdventure extends WrappedObject
{
	@WrappedMethod("asJsonString")
	String staticAsJsonString(WrappedComponentPaper component, Locale locale);
	static String asJsonString(WrappedComponentPaper component, Locale locale)
	{
		return WrappedObject.getStatic(WrappedPaperAdventure.class).staticAsJsonString(component, locale);
	}
}
