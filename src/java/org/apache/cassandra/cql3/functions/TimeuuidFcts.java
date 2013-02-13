/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.cql3.functions;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.cassandra.cql3.ColumnSpecification;
import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.DateType;
import org.apache.cassandra.db.marshal.TimeUUIDType;
import org.apache.cassandra.db.marshal.LongType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.utils.UUIDGen;

public abstract class TimeuuidFcts
{
    public static final Function nowFct = new AbstractFunction("now", TimeUUIDType.instance)
    {
        public ByteBuffer execute(List<ByteBuffer> parameters)
        {
            return ByteBuffer.wrap(UUIDGen.getTimeUUIDBytes());
        }
    };

    public static final Function minTimeuuidFct = new AbstractFunction("mintimeuuid", TimeUUIDType.instance, DateType.instance)
    {
        public ByteBuffer execute(List<ByteBuffer> parameters)
        {
            return ByteBuffer.wrap(UUIDGen.decompose(UUIDGen.minTimeUUID(DateType.instance.compose(parameters.get(0)).getTime())));
        }
    };

    public static final Function maxTimeuuidFct = new AbstractFunction("maxtimeuuid", TimeUUIDType.instance, DateType.instance)
    {
        public ByteBuffer execute(List<ByteBuffer> parameters)
        {
            return ByteBuffer.wrap(UUIDGen.decompose(UUIDGen.maxTimeUUID(DateType.instance.compose(parameters.get(0)).getTime())));
        }
    };

    public static final Function dateOfFct = new AbstractFunction("dateof", DateType.instance, TimeUUIDType.instance)
    {
        public ByteBuffer execute(List<ByteBuffer> parameters)
        {
            return DateType.instance.decompose(new Date(UUIDGen.unixTimestamp(UUIDGen.getUUID(parameters.get(0)))));
        }
    };

    public static final Function unixTimestampOfFct = new AbstractFunction("unixtimestampof", LongType.instance, TimeUUIDType.instance)
    {
        public ByteBuffer execute(List<ByteBuffer> parameters)
        {
            return ByteBufferUtil.bytes(UUIDGen.unixTimestamp(UUIDGen.getUUID(parameters.get(0))));
        }
    };
}
