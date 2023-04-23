import numpy as np
import matplotlib.pyplot as plt

one = np.loadtxt('./output/230423163217.csv', delimiter=',')
ten = np.loadtxt('./output/230423163224.csv', delimiter=',')
hundred = np.loadtxt('./output/230423163228.csv', delimiter=',')
thousand = np.loadtxt('./output/230423163243.csv', delimiter=',')
ten_thousand = np.loadtxt('./output/230423163601.csv', delimiter=',')

one =  one[one[:, 0].argsort()]
one = np.split(one[:,1], np.unique(one[:, 0], return_index=True)[1][1:])
one_mean = np.mean(one, axis=1)

ten =  ten[ten[:, 0].argsort()]
ten = np.split(ten[:,1], np.unique(ten[:, 0], return_index=True)[1][1:])
ten_mean = np.mean(ten, axis=1)

hundred =  hundred[hundred[:, 0].argsort()]
hundred = np.split(hundred[:,1], np.unique(hundred[:, 0], return_index=True)[1][1:])
hundred_mean = np.mean(hundred, axis=1)

thousand =  thousand[thousand[:, 0].argsort()]
thousand = np.split(thousand[:,1], np.unique(thousand[:, 0], return_index=True)[1][1:])
thousand_mean = np.mean(thousand, axis=1)

ten_thousand =  ten_thousand[ten_thousand[:, 0].argsort()]
ten_thousand = np.split(ten_thousand[:,1], np.unique(ten_thousand[:, 0], return_index=True)[1][1:])
ten_thousand_mean = np.mean(ten_thousand, axis=1)

def perdiff(a, b):
    return b * 100 / a

means = [one_mean, ten_mean, hundred_mean, thousand_mean, ten_thousand_mean]
for i in range(0, len(one_mean)):
    print("means: ", means[0][i], means[1][i], means[2][i], means[3][i], means[4][i])
    print("%diff:", perdiff(means[0][i], means[0][i]), perdiff(means[1][i], means[0][i]), perdiff(means[2][i], means[0][i]), perdiff(means[3][i], means[0][i]), perdiff(means[4][i], means[0][i]))
    print()