import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDocumentoComercial } from '../documento-comercial.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../documento-comercial.test-samples';

import { DocumentoComercialService } from './documento-comercial.service';

const requireRestSample: IDocumentoComercial = {
  ...sampleWithRequiredData,
};

describe('DocumentoComercial Service', () => {
  let service: DocumentoComercialService;
  let httpMock: HttpTestingController;
  let expectedResult: IDocumentoComercial | IDocumentoComercial[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DocumentoComercialService);
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

    it('should create a DocumentoComercial', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const documentoComercial = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(documentoComercial).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DocumentoComercial', () => {
      const documentoComercial = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(documentoComercial).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DocumentoComercial', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DocumentoComercial', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DocumentoComercial', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDocumentoComercialToCollectionIfMissing', () => {
      it('should add a DocumentoComercial to an empty array', () => {
        const documentoComercial: IDocumentoComercial = sampleWithRequiredData;
        expectedResult = service.addDocumentoComercialToCollectionIfMissing([], documentoComercial);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentoComercial);
      });

      it('should not add a DocumentoComercial to an array that contains it', () => {
        const documentoComercial: IDocumentoComercial = sampleWithRequiredData;
        const documentoComercialCollection: IDocumentoComercial[] = [
          {
            ...documentoComercial,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDocumentoComercialToCollectionIfMissing(documentoComercialCollection, documentoComercial);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DocumentoComercial to an array that doesn't contain it", () => {
        const documentoComercial: IDocumentoComercial = sampleWithRequiredData;
        const documentoComercialCollection: IDocumentoComercial[] = [sampleWithPartialData];
        expectedResult = service.addDocumentoComercialToCollectionIfMissing(documentoComercialCollection, documentoComercial);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentoComercial);
      });

      it('should add only unique DocumentoComercial to an array', () => {
        const documentoComercialArray: IDocumentoComercial[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const documentoComercialCollection: IDocumentoComercial[] = [sampleWithRequiredData];
        expectedResult = service.addDocumentoComercialToCollectionIfMissing(documentoComercialCollection, ...documentoComercialArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const documentoComercial: IDocumentoComercial = sampleWithRequiredData;
        const documentoComercial2: IDocumentoComercial = sampleWithPartialData;
        expectedResult = service.addDocumentoComercialToCollectionIfMissing([], documentoComercial, documentoComercial2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentoComercial);
        expect(expectedResult).toContain(documentoComercial2);
      });

      it('should accept null and undefined values', () => {
        const documentoComercial: IDocumentoComercial = sampleWithRequiredData;
        expectedResult = service.addDocumentoComercialToCollectionIfMissing([], null, documentoComercial, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentoComercial);
      });

      it('should return initial array if no DocumentoComercial is added', () => {
        const documentoComercialCollection: IDocumentoComercial[] = [sampleWithRequiredData];
        expectedResult = service.addDocumentoComercialToCollectionIfMissing(documentoComercialCollection, undefined, null);
        expect(expectedResult).toEqual(documentoComercialCollection);
      });
    });

    describe('compareDocumentoComercial', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDocumentoComercial(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDocumentoComercial(entity1, entity2);
        const compareResult2 = service.compareDocumentoComercial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDocumentoComercial(entity1, entity2);
        const compareResult2 = service.compareDocumentoComercial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDocumentoComercial(entity1, entity2);
        const compareResult2 = service.compareDocumentoComercial(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
