package io.nuls.contract.idea.plugin.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import io.nuls.contract.idea.plugin.model.NulsAccount;
import io.nuls.contract.idea.plugin.model.NulsContract;
import io.nuls.contract.idea.plugin.model.NulsNode;
import io.nuls.contract.idea.plugin.toolwindow.ui.NulsToolWindowPanel;
import io.nuls.contract.idea.plugin.util.NulsBundle;

import javax.swing.*;

public class DeleteTreeItemAction extends AnAction implements DumbAware {
    private final NulsToolWindowPanel nulsToolWindowPanel;

    public DeleteTreeItemAction(NulsToolWindowPanel nulsToolWindowPanel) {
        super(NulsBundle.message("toolwindow.action.del"),
                NulsBundle.message("toolwindow.action.del.item"), AllIcons.ToolbarDecorator.Remove);
        this.nulsToolWindowPanel = nulsToolWindowPanel;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Object selectedItem = nulsToolWindowPanel.getSelectedItem();

        if (selectedItem instanceof NulsNode) {
            NulsNode node = (NulsNode) selectedItem;
            deleteItem("node", node.toString(),
                    () -> nulsToolWindowPanel.removeTreeItem(node));
            return;
        }

        if (selectedItem instanceof NulsAccount) {
            NulsAccount account = (NulsAccount) selectedItem;
            deleteItem("account", account.toString(),
                    () -> nulsToolWindowPanel.removeTreeItem(account));
            return;
        }

        if (selectedItem instanceof NulsContract) {
            NulsContract contract = (NulsContract) selectedItem;
            deleteItem("collection", contract.toString(),
                    () -> nulsToolWindowPanel.removeTreeItem(contract));
        }
    }

    private void deleteItem(String itemTypeLabel, String itemLabel, Runnable deleteOperation) {
        int result = JOptionPane.showConfirmDialog(null,
                String.format("Do you REALLY want to remove the '%s' %s?", itemLabel, itemTypeLabel),
                "WARNING",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            deleteOperation.run();
        }
    }
}
