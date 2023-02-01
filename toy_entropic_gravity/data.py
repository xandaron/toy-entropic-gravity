import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from sklearn.metrics import r2_score

data = np.loadtxt('./output/230131164340.csv', delimiter=',')
train_size = round(data.shape[0] * 0.75)

x = data[:,0]
print(x)
x = np.log(1 / x)
print(x)
x = x.reshape(-1, 1)
x_train = x[:train_size]
x_test = x[train_size:]

y = data[:,1]
y = np.log(y)
y_train = y[:train_size]
y_test = y[train_size:]

reg = LinearRegression()
reg.fit(x_train, y_train)

y_pred = reg.predict(x_test)
m = reg.coef_[0]
c = reg.intercept_

print('Gradient:', m)
print('Intercept:', c)
print('Coefficient of determination:', r2_score(y_test, y_pred))

fig, ax = plt.subplots()
ax.scatter(x, y)
ax.plot(x, c + m*x, color='red', linestyle='-')
plt.text(1, 17, 'ln(F) = {0:.2f} + {1:.2f}ln(1/x)'.format(c, m), size=14)
plt.show()