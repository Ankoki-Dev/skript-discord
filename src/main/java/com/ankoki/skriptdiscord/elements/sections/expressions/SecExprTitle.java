package com.ankoki.skriptdiscord.elements.sections.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.ExpressionType;
import com.ankoki.skriptdiscord.elements.sections.SecEmbed;
import com.ankoki.skriptdiscord.misc.skript.SectionExpression;
import org.bukkit.event.Event;

public class SecExprTitle extends SectionExpression<String, SecEmbed> {

	static {
		Skript.registerExpression(SecExprTitle.class, String.class, ExpressionType.SIMPLE,
				"[the] title");
	}

	@Override
	protected String[] get(Event event) {
		return new String[]{this.getSection().getTitle()};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	protected Class<? extends SecEmbed> getSectionType() {
		return SecEmbed.class;
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		return mode == ChangeMode.SET ? new Class<?>[]{String.class} : null;
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		assert mode == ChangeMode.SET;
		if (delta.length != 1)
			return;
		this.getSection().setTitle(String.valueOf(delta[0]));
	}

	@Override
	public String toString(Event event, boolean b) {
		return "the title";
	}

}
