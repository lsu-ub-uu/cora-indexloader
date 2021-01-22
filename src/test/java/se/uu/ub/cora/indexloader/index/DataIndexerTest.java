/*
 * Copyright 2021 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.indexloader.index;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataRecord;
import se.uu.ub.cora.indexloader.CoraClientSpy;

public class DataIndexerTest {

	private DataIndexerImp indexer;
	private CoraClientSpy coraClientSpy;

	@BeforeMethod
	public void setUp() {
		coraClientSpy = new CoraClientSpy();
		indexer = DataIndexerImp.usingCoraClient(coraClientSpy);

	}

	@Test
	public void testInit() {
		assertSame(indexer.getCoraClient(), coraClientSpy);
	}

	@Test
	public void testCorrectIndexedData() {
		indexer.indexDataWithRecordType("someRecordType");
		assertEquals(coraClientSpy.recordTypes.get(0), "someRecordType");

		List<ClientDataRecord> returnedListOfRecords = coraClientSpy.returnedListOfRecords;
		assertSame(coraClientSpy.indexedRecords.get(0), returnedListOfRecords.get(0));
		assertSame(coraClientSpy.indexedRecords.get(1), returnedListOfRecords.get(1));
		assertSame(coraClientSpy.indexedRecords.get(2), returnedListOfRecords.get(2));

	}

	@Test
	public void testCatchErrorWhenIndexing() {
		coraClientSpy.throwErrorWhenIndexing = true;
		indexer.indexDataWithRecordType("someRecordType");
		assertEquals(coraClientSpy.recordTypes.get(0), "someRecordType");
	}
}
