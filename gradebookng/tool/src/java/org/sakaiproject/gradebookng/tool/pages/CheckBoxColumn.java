package org.sakaiproject.gradebookng.tool.pages;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * CheckBoxColumn
 *
 * @author Joseph Gillman
 */
public abstract class CheckBoxColumn extends AbstractColumn {
	public CheckBoxColumn(IModel displayModel) {
		super(displayModel);
	}

	protected abstract IModel newCheckBoxModel(IModel rowModel);

	protected CheckBox newCheckBox(String id, IModel checkModel) {
		return new CheckBox("check", checkModel);
	}

	private class CheckPanel extends Panel {
		public CheckPanel(String id, IModel checkModel) {
			super(id);
			add(newCheckBox("check", checkModel));
		}
	}

	public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		cellItem.add(new CheckPanel(componentId, newCheckBoxModel(rowModel)));
        cellItem.add(new AttributeModifier("style", "display: table-cell; text-align: center;"));
	}
}