package org.pentaho.agilebi.modeler.nodes;

import org.pentaho.agilebi.modeler.ColumnBackedNode;
import org.pentaho.agilebi.modeler.propforms.LevelsPropertiesForm;
import org.pentaho.agilebi.modeler.propforms.ModelerNodePropertiesForm;
import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.ui.xul.stereotype.Bindable;

import java.io.Serializable;

/**
 * Created: 3/18/11
 *
 * @author rfellows
 */
public class BaseColumnBackedMetaData<T extends AbstractMetaDataModelNode> extends AbstractMetaDataModelNode<T> implements Serializable, ColumnBackedNode {
  private static final long serialVersionUID = -7342401951588541248L;
  protected String name;
  protected String columnName;
  protected transient T parent;
  protected transient LogicalColumn logicalColumn;
  protected Boolean uniqueMembers = false;
  private static final String IMAGE = "images/sm_level_icon.png";

  public BaseColumnBackedMetaData(){

  }

  @Override
  public String getValidImage() {
    return IMAGE;
  }

  public BaseColumnBackedMetaData(T parent, String name) {
    this.parent = parent;
    this.name = name;
    this.columnName = name;
  }
  
  @Bindable
  public String getName() {
    return name;
  }

  @Bindable
  public String getDisplayName() {
    return getName();
  }

  @Bindable
  public void setName( String name ) {
    if (!name.equals(this.name)) {
      String oldName = this.name;
      this.name = name;
      this.firePropertyChange("name", oldName, name); //$NON-NLS-1$
      this.firePropertyChange("displayName", oldName, name); //$NON-NLS-1$
      validateNode();
    }
  }

  @Bindable
  public String getColumnName() {
    return columnName;
  }

  @Bindable
  public void setColumnName( String columnName ) {
    this.columnName = columnName;
  }

  public T getParent() {
    return parent;
  }

  public void setParent( T md ) {
    this.parent = md;
  }

  @Bindable
  public LogicalColumn getLogicalColumn() {
    return logicalColumn;
  }

  public void setLogicalColumn( LogicalColumn col ) {
    LogicalColumn prevVal = this.logicalColumn;
    this.logicalColumn = col;
    validateNode();
    firePropertyChange("logicalColumn", prevVal, col);
  }

  public void setUniqueMembers( Boolean uniqueMembers ) {
    this.uniqueMembers = uniqueMembers;
  }

  public Boolean isUniqueMembers() {
    return uniqueMembers;
  }

  @Override
  public void validate() {
    String prevMessages = getValidationMessagesString();
    valid = true;
    validationMessages.clear();
    // check name
    if (name == null || "".equals(name)) {
      validationMessages.add("Name is empty");
      valid = false;
    }
    if (logicalColumn == null) {
      validationMessages.add("Column is missing");
      valid = false;
    }
  }

  @Override
  public Class<? extends ModelerNodePropertiesForm> getPropertiesForm() {
     return LevelsPropertiesForm.class;
  }

  @Bindable
  public boolean isUiExpanded() {
    return true;
  }

  @Bindable
  public boolean isEditingDisabled() {
    return false;
  }

}