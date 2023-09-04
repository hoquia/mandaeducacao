import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INotasGeralDisciplina } from '../notas-geral-disciplina.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../notas-geral-disciplina.test-samples';

import { NotasGeralDisciplinaService, RestNotasGeralDisciplina } from './notas-geral-disciplina.service';

const requireRestSample: RestNotasGeralDisciplina = {
  ...sampleWithRequiredData,
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('NotasGeralDisciplina Service', () => {
  let service: NotasGeralDisciplinaService;
  let httpMock: HttpTestingController;
  let expectedResult: INotasGeralDisciplina | INotasGeralDisciplina[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NotasGeralDisciplinaService);
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

    it('should create a NotasGeralDisciplina', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const notasGeralDisciplina = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(notasGeralDisciplina).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NotasGeralDisciplina', () => {
      const notasGeralDisciplina = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(notasGeralDisciplina).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NotasGeralDisciplina', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NotasGeralDisciplina', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NotasGeralDisciplina', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNotasGeralDisciplinaToCollectionIfMissing', () => {
      it('should add a NotasGeralDisciplina to an empty array', () => {
        const notasGeralDisciplina: INotasGeralDisciplina = sampleWithRequiredData;
        expectedResult = service.addNotasGeralDisciplinaToCollectionIfMissing([], notasGeralDisciplina);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(notasGeralDisciplina);
      });

      it('should not add a NotasGeralDisciplina to an array that contains it', () => {
        const notasGeralDisciplina: INotasGeralDisciplina = sampleWithRequiredData;
        const notasGeralDisciplinaCollection: INotasGeralDisciplina[] = [
          {
            ...notasGeralDisciplina,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNotasGeralDisciplinaToCollectionIfMissing(notasGeralDisciplinaCollection, notasGeralDisciplina);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NotasGeralDisciplina to an array that doesn't contain it", () => {
        const notasGeralDisciplina: INotasGeralDisciplina = sampleWithRequiredData;
        const notasGeralDisciplinaCollection: INotasGeralDisciplina[] = [sampleWithPartialData];
        expectedResult = service.addNotasGeralDisciplinaToCollectionIfMissing(notasGeralDisciplinaCollection, notasGeralDisciplina);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(notasGeralDisciplina);
      });

      it('should add only unique NotasGeralDisciplina to an array', () => {
        const notasGeralDisciplinaArray: INotasGeralDisciplina[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const notasGeralDisciplinaCollection: INotasGeralDisciplina[] = [sampleWithRequiredData];
        expectedResult = service.addNotasGeralDisciplinaToCollectionIfMissing(notasGeralDisciplinaCollection, ...notasGeralDisciplinaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const notasGeralDisciplina: INotasGeralDisciplina = sampleWithRequiredData;
        const notasGeralDisciplina2: INotasGeralDisciplina = sampleWithPartialData;
        expectedResult = service.addNotasGeralDisciplinaToCollectionIfMissing([], notasGeralDisciplina, notasGeralDisciplina2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(notasGeralDisciplina);
        expect(expectedResult).toContain(notasGeralDisciplina2);
      });

      it('should accept null and undefined values', () => {
        const notasGeralDisciplina: INotasGeralDisciplina = sampleWithRequiredData;
        expectedResult = service.addNotasGeralDisciplinaToCollectionIfMissing([], null, notasGeralDisciplina, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(notasGeralDisciplina);
      });

      it('should return initial array if no NotasGeralDisciplina is added', () => {
        const notasGeralDisciplinaCollection: INotasGeralDisciplina[] = [sampleWithRequiredData];
        expectedResult = service.addNotasGeralDisciplinaToCollectionIfMissing(notasGeralDisciplinaCollection, undefined, null);
        expect(expectedResult).toEqual(notasGeralDisciplinaCollection);
      });
    });

    describe('compareNotasGeralDisciplina', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNotasGeralDisciplina(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNotasGeralDisciplina(entity1, entity2);
        const compareResult2 = service.compareNotasGeralDisciplina(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNotasGeralDisciplina(entity1, entity2);
        const compareResult2 = service.compareNotasGeralDisciplina(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNotasGeralDisciplina(entity1, entity2);
        const compareResult2 = service.compareNotasGeralDisciplina(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
