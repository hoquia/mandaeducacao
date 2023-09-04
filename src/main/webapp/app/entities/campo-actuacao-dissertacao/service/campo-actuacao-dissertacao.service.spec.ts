import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICampoActuacaoDissertacao } from '../campo-actuacao-dissertacao.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../campo-actuacao-dissertacao.test-samples';

import { CampoActuacaoDissertacaoService } from './campo-actuacao-dissertacao.service';

const requireRestSample: ICampoActuacaoDissertacao = {
  ...sampleWithRequiredData,
};

describe('CampoActuacaoDissertacao Service', () => {
  let service: CampoActuacaoDissertacaoService;
  let httpMock: HttpTestingController;
  let expectedResult: ICampoActuacaoDissertacao | ICampoActuacaoDissertacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CampoActuacaoDissertacaoService);
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

    it('should create a CampoActuacaoDissertacao', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const campoActuacaoDissertacao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(campoActuacaoDissertacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CampoActuacaoDissertacao', () => {
      const campoActuacaoDissertacao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(campoActuacaoDissertacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CampoActuacaoDissertacao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CampoActuacaoDissertacao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CampoActuacaoDissertacao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCampoActuacaoDissertacaoToCollectionIfMissing', () => {
      it('should add a CampoActuacaoDissertacao to an empty array', () => {
        const campoActuacaoDissertacao: ICampoActuacaoDissertacao = sampleWithRequiredData;
        expectedResult = service.addCampoActuacaoDissertacaoToCollectionIfMissing([], campoActuacaoDissertacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(campoActuacaoDissertacao);
      });

      it('should not add a CampoActuacaoDissertacao to an array that contains it', () => {
        const campoActuacaoDissertacao: ICampoActuacaoDissertacao = sampleWithRequiredData;
        const campoActuacaoDissertacaoCollection: ICampoActuacaoDissertacao[] = [
          {
            ...campoActuacaoDissertacao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCampoActuacaoDissertacaoToCollectionIfMissing(
          campoActuacaoDissertacaoCollection,
          campoActuacaoDissertacao
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CampoActuacaoDissertacao to an array that doesn't contain it", () => {
        const campoActuacaoDissertacao: ICampoActuacaoDissertacao = sampleWithRequiredData;
        const campoActuacaoDissertacaoCollection: ICampoActuacaoDissertacao[] = [sampleWithPartialData];
        expectedResult = service.addCampoActuacaoDissertacaoToCollectionIfMissing(
          campoActuacaoDissertacaoCollection,
          campoActuacaoDissertacao
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(campoActuacaoDissertacao);
      });

      it('should add only unique CampoActuacaoDissertacao to an array', () => {
        const campoActuacaoDissertacaoArray: ICampoActuacaoDissertacao[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const campoActuacaoDissertacaoCollection: ICampoActuacaoDissertacao[] = [sampleWithRequiredData];
        expectedResult = service.addCampoActuacaoDissertacaoToCollectionIfMissing(
          campoActuacaoDissertacaoCollection,
          ...campoActuacaoDissertacaoArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const campoActuacaoDissertacao: ICampoActuacaoDissertacao = sampleWithRequiredData;
        const campoActuacaoDissertacao2: ICampoActuacaoDissertacao = sampleWithPartialData;
        expectedResult = service.addCampoActuacaoDissertacaoToCollectionIfMissing([], campoActuacaoDissertacao, campoActuacaoDissertacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(campoActuacaoDissertacao);
        expect(expectedResult).toContain(campoActuacaoDissertacao2);
      });

      it('should accept null and undefined values', () => {
        const campoActuacaoDissertacao: ICampoActuacaoDissertacao = sampleWithRequiredData;
        expectedResult = service.addCampoActuacaoDissertacaoToCollectionIfMissing([], null, campoActuacaoDissertacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(campoActuacaoDissertacao);
      });

      it('should return initial array if no CampoActuacaoDissertacao is added', () => {
        const campoActuacaoDissertacaoCollection: ICampoActuacaoDissertacao[] = [sampleWithRequiredData];
        expectedResult = service.addCampoActuacaoDissertacaoToCollectionIfMissing(campoActuacaoDissertacaoCollection, undefined, null);
        expect(expectedResult).toEqual(campoActuacaoDissertacaoCollection);
      });
    });

    describe('compareCampoActuacaoDissertacao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCampoActuacaoDissertacao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCampoActuacaoDissertacao(entity1, entity2);
        const compareResult2 = service.compareCampoActuacaoDissertacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCampoActuacaoDissertacao(entity1, entity2);
        const compareResult2 = service.compareCampoActuacaoDissertacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCampoActuacaoDissertacao(entity1, entity2);
        const compareResult2 = service.compareCampoActuacaoDissertacao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
