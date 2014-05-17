/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v2_1.pprint

import org.neo4j.cypher.internal.helpers.PartialFunctionSupport
import scala.reflect.ClassTag

abstract class NestedDocGenerator[T: ClassTag] extends RecursiveDocGenerator[T] {
  final val docGen = PartialFunctionSupport.fix(this)

  protected def instance: RecursiveDocGenerator[T]

  def isDefinedAt(v: T) = instance.isDefinedAt(v)
  def apply(v: T) = instance(v)

  final def uplifted[S >: T: ClassTag]: RecursiveDocGenerator[S] =
    PartialFunctionSupport.uplift[T, DocGenerator[T] => Doc, S](this)
}
