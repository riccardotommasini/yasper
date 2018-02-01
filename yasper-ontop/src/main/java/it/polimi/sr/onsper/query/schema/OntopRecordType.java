package it.polimi.sr.onsper.query.schema;

import com.google.common.base.Preconditions;
import org.apache.calcite.rel.type.RelDataTypeField;
import org.apache.calcite.rel.type.RelRecordType;

import java.util.List;
import java.util.Objects;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Record type based on a Java class. The fields of the type are the fields
 * of the class.
 * <p>
 * <p><strong>NOTE: This class is experimental and subject to
 * change/removal without notice</strong>.</p>
 */

public class OntopRecordType extends RelRecordType {

    final Class clazz;

    public OntopRecordType(List<RelDataTypeField> fields, Class clazz) {
        super(fields);
        this.clazz = Preconditions.checkNotNull(clazz);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || obj instanceof OntopRecordType
                && fieldList.equals(((OntopRecordType) obj).fieldList)
                && clazz == ((OntopRecordType) obj).clazz;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldList, clazz);
    }

}

// End OntopRecordType.java