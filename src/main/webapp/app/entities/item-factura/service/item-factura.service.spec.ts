import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IItemFactura } from '../item-factura.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../item-factura.test-samples';

import { ItemFacturaService, RestItemFactura } from './item-factura.service';

const requireRestSample: RestItemFactura = {
  ...sampleWithRequiredData,
  emissao: sampleWithRequiredData.emissao?.format(DATE_FORMAT),
  expiracao: sampleWithRequiredData.expiracao?.format(DATE_FORMAT),
};

describe('ItemFactura Service', () => {
  let service: ItemFacturaService;
  let httpMock: HttpTestingController;
  let expectedResult: IItemFactura | IItemFactura[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ItemFacturaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ItemFactura', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const itemFactura = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(itemFactura).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ItemFactura', () => {
      const itemFactura = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(itemFactura).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ItemFactura', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ItemFactura', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ItemFactura', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addItemFacturaToCollectionIfMissing', () => {
      it('should add a ItemFactura to an empty array', () => {
        const itemFactura: IItemFactura = sampleWithRequiredData;
        expectedResult = service.addItemFacturaToCollectionIfMissing([], itemFactura);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(itemFactura);
      });

      it('should not add a ItemFactura to an array that contains it', () => {
        const itemFactura: IItemFactura = sampleWithRequiredData;
        const itemFacturaCollection: IItemFactura[] = [
          {
            ...itemFactura,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addItemFacturaToCollectionIfMissing(itemFacturaCollection, itemFactura);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ItemFactura to an array that doesn't contain it", () => {
        const itemFactura: IItemFactura = sampleWithRequiredData;
        const itemFacturaCollection: IItemFactura[] = [sampleWithPartialData];
        expectedResult = service.addItemFacturaToCollectionIfMissing(itemFacturaCollection, itemFactura);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(itemFactura);
      });

      it('should add only unique ItemFactura to an array', () => {
        const itemFacturaArray: IItemFactura[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const itemFacturaCollection: IItemFactura[] = [sampleWithRequiredData];
        expectedResult = service.addItemFacturaToCollectionIfMissing(itemFacturaCollection, ...itemFacturaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const itemFactura: IItemFactura = sampleWithRequiredData;
        const itemFactura2: IItemFactura = sampleWithPartialData;
        expectedResult = service.addItemFacturaToCollectionIfMissing([], itemFactura, itemFactura2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(itemFactura);
        expect(expectedResult).toContain(itemFactura2);
      });

      it('should accept null and undefined values', () => {
        const itemFactura: IItemFactura = sampleWithRequiredData;
        expectedResult = service.addItemFacturaToCollectionIfMissing([], null, itemFactura, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(itemFactura);
      });

      it('should return initial array if no ItemFactura is added', () => {
        const itemFacturaCollection: IItemFactura[] = [sampleWithRequiredData];
        expectedResult = service.addItemFacturaToCollectionIfMissing(itemFacturaCollection, undefined, null);
        expect(expectedResult).toEqual(itemFacturaCollection);
      });
    });

    describe('compareItemFactura', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareItemFactura(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareItemFactura(entity1, entity2);
        const compareResult2 = service.compareItemFactura(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareItemFactura(entity1, entity2);
        const compareResult2 = service.compareItemFactura(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareItemFactura(entity1, entity2);
        const compareResult2 = service.compareItemFactura(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
