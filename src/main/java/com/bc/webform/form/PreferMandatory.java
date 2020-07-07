package com.bc.webform.form;

import com.bc.webform.form.member.FormMember;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.Comparator;
import java.util.logging.Level;


/**
 * @(#)FormFieldComparator.java   10-May-2015 18:16:05
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class PreferMandatory<F, V> implements Comparator<FormMember<F, V>>, Serializable {
    
    private transient static final Logger LOG = Logger.getLogger(PreferMandatory.class.getName());
    
    public PreferMandatory() { }
    
    @Override
    public int compare(FormMember<F, V> lhs, FormMember<F, V> rhs) {
        
        final boolean lhsOptional = lhs.isOptional();

        final boolean rhsOptional = rhs.isOptional();

        if(LOG.isLoggable(Level.FINER)){
            LOG.log(Level.FINER, "Optional: {0}, lhs: {1}\nOptional: {2}, rhs: {3}", 
                    new Object[]{lhsOptional, lhs, rhsOptional, rhs});
        }
        
        return Boolean.compare(lhsOptional, rhsOptional);
    }
}
